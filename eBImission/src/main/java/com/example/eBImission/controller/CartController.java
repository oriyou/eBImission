package com.example.eBImission.controller;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartProductDto;
import com.example.eBImission.entity.dto.CartRequest;
import com.example.eBImission.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Cart> registerCartProduct(@RequestBody CartRequest cartRequest) {

        return cartService.registerCart(cartRequest);
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
