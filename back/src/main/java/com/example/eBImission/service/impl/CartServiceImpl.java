package com.example.eBImission.service.impl;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartGroupDto;
import com.example.eBImission.entity.dto.CartProductInfoDto;
import com.example.eBImission.entity.dto.ProductInfoResponse;
import com.example.eBImission.service.CartService;
import com.example.eBImission.repository.CartRepository;
import com.example.eBImission.utils.WebClientUtil;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final String END_POINT_URI = "https://pbf.lotteon.com/product/v1/detail/productDetailList?dataType=LIGHT2";

    private final CartRepository cartRepository;
    private final WebClient webClient;
    private final WebClientUtil webClientUtil;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Flux<CartProductInfoDto> retrieveCart() {

        Flux<Cart> cartProducts = cartRepository.findAllCartOrderByRegDttm();

        Mono<CartProductInfoDto[]> productsInfo = webClientUtil.postRequest(END_POINT_URI, cartProducts, Cart.class)
                .bodyToMono(ProductInfoResponse.class)
                .map(response -> response.getData())
                .cache();

        return cartProducts
                .concatMap(prod -> Flux.just(prod.toDto()))
                .concatMap(prod -> productsInfo.map(infoDtoArr -> {
                    for(CartProductInfoDto dto : infoDtoArr) {
                        if (dto.isEqual(prod)) {
                            addValue(prod, dto);
                        }
                    }
                    return prod;
                }));
    }

    @Override
    public Flux<CartProductInfoDto> retrieveCartAfterGrouping() {
        return cartRepository.findAllCartOrderByRegDttm()
                .groupBy(cart ->
                        new CartGroupDto(cart.getSpdNo(), cart.getSitmNo(), cart.getTrNo()))
                .concatMap(group -> {
                    log.info("Group key: {}", group.key());
                    return webClientUtil.postRequest(END_POINT_URI, Flux.just(group.key()), CartGroupDto.class)
                            .bodyToFlux(ProductInfoResponse.class)
                            .flatMap(resp -> Flux.just(resp.getData()[0]))
                            .flatMap(apiData -> {
                                log.info("Response Data: {}", apiData);
                                return group.map(orgData -> orgData.toDto())
                                        .concatMap(orgData -> {
                                            log.info("orgData: {}", orgData);
                                            return addValue(orgData, apiData);
                                        });
                            });
                });
    }

    @Override
    public Mono<Map<String, Collection<CartProductInfoDto>>> retrieveCartByMap() {

        Flux<Cart> cartProducts = cartRepository.findAllCartOrderByRegDttm();
        // Webclient의 기존 설정값을 상속해서 사용할 수 있는 mutate() 함수를 통해 build()
        return webClientUtil.postRequest(END_POINT_URI, cartProducts, Cart.class)
                .bodyToMono(JsonNode.class)
                .map(e -> e.path("data"))
                .flatMapMany(Flux::fromIterable)
                .map(e -> mapper.convertValue(e, CartProductInfoDto.class))
                .zipWith(cartProducts, (product, cartProduct) -> {
                    product.setCartSn(cartProduct.getCartSn());
                    product.setOdQty(cartProduct.getOdQty());
                    product.setMbNo(cartProduct.getMbNo());
                    product.setRegDttm(cartProduct.getRegDttm());

                    return product;
                })
                .collectMultimap(cartProductInfoDto -> cartProductInfoDto.getLrtrNo());
    }

    private Flux<CartProductInfoDto> addValue(CartProductInfoDto orgData, CartProductInfoDto apiData) {
        orgData.setTrNo(apiData.getTrNo());
        orgData.setLrtrNo(apiData.getLrtrNo());
        orgData.setSpdNm(apiData.getSpdNm());
        orgData.setBrdNm(apiData.getBrdNm());
        orgData.setSlPrc(apiData.getSlPrc());
        orgData.setEstmtDlvTxt(apiData.getEstmtDlvTxt());
        orgData.setImgJsn(apiData.getImgJsn());
        return Flux.just(orgData);
    }

    @Override
    public Mono<Cart> registerCart(CartDto cartDtoRequest) {

        return webClientUtil.postRequest(END_POINT_URI, Flux.just(cartDtoRequest), CartDto.class)
                .bodyToMono(ProductInfoResponse.class)
                .map(e -> {
                    System.out.println(e.getReturnCode());
                    // 500에러 일때 RuntimeException
                    if(e.getReturnCode().equals("500"))
                        throw new RuntimeException("500 Error");
                    return e.getData()[0];
                })
                // returnCode가 200이고 lrtrNo가 null이 아닌지 체크
                .filter(cart -> cart.getReturnCode().equals("200") && cart.getLrtrNo() != null)
                .map(dto -> mapper.convertValue(dto, Cart.class))
                .flatMap(cart -> {
                    cart.setMbNo(cartDtoRequest.getMbNo());
                    cart.setOdQty(cartDtoRequest.getOdQty());

                    return cartRepository.save(cart);
                });
    }

    @Override
    public Mono<Cart> modifyCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Flux<Integer> removeCart(Flux<String> cartSnFlux) {
        return cartSnFlux.flatMap(cartRepository::deleteCartByCartSn);
    }

}
