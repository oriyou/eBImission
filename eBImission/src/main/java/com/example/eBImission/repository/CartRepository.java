package com.example.eBImission.repository;

import com.example.eBImission.entity.Cart;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {

    @Query("SELECT * FROM om_cart ORDER BY REG_DTTM DESC")
    Flux<Cart> findAllOrderByRegDttm();
}
