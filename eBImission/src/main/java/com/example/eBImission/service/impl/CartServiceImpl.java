package com.example.eBImission.service.impl;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
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

        Flux<CartProductInfoDto> productsInfo = webClientUtil.postRequest(END_POINT_URI, cartProducts, Cart.class)
                .bodyToMono(JsonNode.class)
                .flatMapMany(response -> Flux.fromIterable(response.path("data")))
                .map(jsonNode -> mapper.convertValue(jsonNode, CartProductInfoDto.class));


        // cartSn, odQty, mbNo 추가
        return productsInfo.zipWith(cartProducts, (product, cartProduct) -> {
            product.setCartSn(cartProduct.getCartSn());
            product.setOdQty(cartProduct.getOdQty());
            product.setMbNo(cartProduct.getMbNo());
            product.setRegDttm(cartProduct.getRegDttm());
            return product;
        });


    }

    @Override
    public Mono<Map<String, Collection<CartProductInfoDto>>> retrieveCart2() {

        Flux<Cart> cart = cartRepository.findAllCartOrderByRegDttm();
        // Webclient의 기존 설정값을 상속해서 사용할 수 있는 mutate() 함수를 통해 build()
        return webClient.mutate()
                .build()
                .post()
                .uri(END_POINT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(cart, Cart.class)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(e -> e.path("data"))
                .flatMapMany(Flux::fromIterable)
                .map(e -> mapper.convertValue(e, CartProductInfoDto.class))
                .zipWith(cart, (product, cartProduct) -> {
                    product.setCartSn(cartProduct.getCartSn());
                    product.setOdQty(cartProduct.getOdQty());
                    product.setMbNo(cartProduct.getMbNo());
                    product.setRegDttm(cartProduct.getRegDttm());

                    return product;
                })
                .collectMultimap(cartProductInfoDto -> cartProductInfoDto.getLrtrNo());
    }

    @Override
    public Flux<Object> retrieveCart3() {
//        Flux<GroupedFlux<Cart, Object>> group = cartRepository.findAllCartOrderByRegDttm().groupBy(cartProd -> new Cart(cartProd.getLrtrNo(), cartProd.getSpdNo(), cartProd.getSitmNo());
        Flux<Cart> cartProducts = cartRepository.findAllCartOrderByRegDttm();

        Flux<CartProductInfoDto> productsInfo = webClientUtil.postRequest(END_POINT_URI, cartProducts, Cart.class)
                .bodyToMono(JsonNode.class)
                .flatMapMany(response -> Flux.fromIterable(response.path("data")))
                .map(jsonNode -> mapper.convertValue(jsonNode, CartProductInfoDto.class));

        return cartProducts.concatMap(cartProduct ->
            Flux.just(productsInfo.map(dto -> {
                if(cartProduct.getSpdNo().equals(dto.getSpdNo())){
                    return addValue(cartProduct, dto);
                } else {
                    return dto;
                }
            })
        );
    }

    private Flux<CartProductInfoDto> addValue(Cart cart, CartProductInfoDto dto) {
        CartProductInfoDto cartDto = cart.toDto();
        cartDto.setTrNo(dto.getTrNo());
        cartDto.setLrtrNo(dto.getLrtrNo());
        return Flux.just(cartDto);
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
                    return e.getData();
                })
                .flatMapMany(cartDtoArr -> Flux.just(cartDtoArr))
                .next()
                // returnCode가 200이고 lrtrNo가 null이 아닌지 체크
                .filter(cart -> cart.getReturnCode().equals("200") && cart.getLrtrNo() != null)
                .map(cartDto -> {
                    cartDto.setMbNo(cartDtoRequest.getMbNo());
                    cartDto.setOdQty(cartDtoRequest.getOdQty());

                    Cart cart = cartDto.toCart();
                    cartRepository.save(cart).subscribe();

                    return cart;
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
