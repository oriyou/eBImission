package com.example.eBImission.service;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartProductInfoDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {

    Flux<CartProductInfoDto> retrieveCart();
    Mono<Cart> registerCart(CartDto cartDtoRequest);
    Mono<Cart> modifyCart(Cart cart);
    Flux<Cart> removeCart(Flux<CartDto> cartArr);
}
