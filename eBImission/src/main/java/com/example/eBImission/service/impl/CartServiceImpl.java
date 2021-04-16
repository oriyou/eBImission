package com.example.eBImission.service.impl;

import com.example.eBImission.config.WebClientConfig;
import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartProductDto;
import com.example.eBImission.service.CartService;
import com.example.eBImission.repository.CartRepository;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final WebClient webClient;

    @Override
    public Flux<CartProductDto> retrieveCart() {

        final String END_POINT_URI = "https://pbf.lotteon.com/product/v1/detail/productDetailList?dataType=LIGHT2";
//        Flux<Cart> cart = cartRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        Flux<Cart> cart = cartRepository.findAllOrderByRegDttm();

        /*
        WebClient webClient = WebClient.create();
        Flux<CartProductDto> productInfo = webClient.post()
                        .uri(END_POINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(cart, Cart.class))
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(e -> e.path("data"))
                        .flatMapMany(Flux::fromIterable)
                        .map(e -> new Gson().fromJson(String.valueOf(e), CartProductDto.class));
        */


        // Webclient의 기존 설정값을 상속해서 사용할 수 있는 mutate() 함수를 통해 build()
        Flux<CartProductDto> productInfo = webClient.mutate()
                        .build()
                        .post()
                        .uri(END_POINT_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(cart, Cart.class))
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(e -> e.path("data"))
                        .flatMapMany(Flux::fromIterable)
                        .map(e -> mapper.convertValue(e, CartProductDto.class));


        // cartSn, odQty, mbNo 추가
        return productInfo.zipWith(cart, (product, cartProduct) -> {
            product.setCartSn(cartProduct.getCartSn());
            product.setOdQty(cartProduct.getOdQty());
            product.setMbNo(cartProduct.getMbNo());

            return product;
        });
    }

    @Override
    public Mono<Cart> registerCart(Cart cart) {
        LocalDateTime date = LocalDateTime.now();
        cart.setRegDttm(date);
        return cartRepository.save(cart);
    }

    @Override
    public Mono<Cart> modifyCart(Cart cart) {
        LocalDateTime date = LocalDateTime.now();
        cart.setModDttm(date);
        return cartRepository.save(cart);
    }

    @Override
    public Mono<Void> removeCart(Cart cart) {
        return cartRepository.delete(cart);
    }

}
