package com.example.eBImission.controller;

import com.example.eBImission.entity.Cart;
import com.example.eBImission.entity.dto.CartDto;
import com.example.eBImission.entity.dto.CartProductInfoDto;
import com.example.eBImission.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Flux<CartProductInfoDto> getCartProducts() {
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

    @PostMapping("/remove")
    public Flux<Integer> deleteCart(@RequestBody String[] cartSnArr) {
        return cartService.removeCart(Flux.just(cartSnArr));
    }

    @GetMapping("/map")
    public Mono<Map<String, Collection<CartProductInfoDto>>> getCartProductsByMap() {
        return cartService.retrieveCartByMap();
    }

    @GetMapping("/grouping")
    public Flux<CartProductInfoDto> getCartProductsAfterGrouping() {
        return cartService.retrieveCartAfterGrouping();
    }

}
