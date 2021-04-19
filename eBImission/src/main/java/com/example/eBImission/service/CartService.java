package com.example.eBImission.service;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartProductDto;
import com.example.eBImission.entity.dto.CartRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {

    Flux<CartProductDto> retrieveCart();
    Mono<Cart> registerCart(CartRequest cartRequest);
    Mono<Cart> modifyCart(Cart cart);
    Mono<Void> removeCart(Cart cart);
}
