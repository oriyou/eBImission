package com.example.eBImission.service;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartProductInfoDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartService {

    Flux<CartProductInfoDto> retrieveCart();
    Mono<Map<String, Collection<CartProductInfoDto>>> retrieveCartByMap();
    Flux<CartProductInfoDto> retrieveCartAfterGrouping();
    Mono<Cart> registerCart(CartDto cartDto);
    Mono<Cart> modifyCart(Cart cart);
    Flux<Integer> removeCart(Flux<String> cartSnFlux);
}
