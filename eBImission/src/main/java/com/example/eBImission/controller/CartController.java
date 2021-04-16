package com.example.eBImission.controller;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartProductDto;
import com.example.eBImission.entity.dto.ProductInfoDto;
import com.example.eBImission.service.CartService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Flux<CartProductDto> getCartProducts() {
        return cartService.retrieveCart();
    }

    @PostMapping
    public Mono<Cart> registerCartProduct(@RequestBody CartDto cartDto) {

        return cartService.registerCart(cartDto);
    }

    @PutMapping
    public Mono<Cart> modifyOdQty(@RequestBody Cart cart) {
        return cartService.modifyCart(cart);
    }

    @DeleteMapping
    public Mono<Void> deleteCart(@RequestBody Cart cart) {
        return cartService.removeCart(cart);
    }

}
