package com.example.eBImission.config;

import com.example.eBImission.entity.Cart;
import org.reactivestreams.Publisher;
import org.springframework.cglib.core.Local;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CartCallback implements BeforeConvertCallback<Cart> {

    private static AtomicInteger cartSeq = new AtomicInteger();

    @Override
    public Publisher<Cart> onBeforeConvert(Cart cart, SqlIdentifier table) {
        System.out.println("*************************beforeConvert**************************");
        System.out.println(cart);

        if(cart.getCartSn() == null || cart.getCartSn().equals(""))
            return Mono.just(setCartSnAndGet(cart));
        return Mono.just(cart);
    }

    public Cart setCartSnAndGet(Cart cart) {
        cart.setCartSn(getTodayStr()+getCartSeq());
        return cart;
    }

    private String getTodayStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    private static String getCartSeq() {
        int generatedSeq = cartSeq.incrementAndGet();
        return String.format("%04d", generatedSeq);
    }

}
