package com.example.eBImission.entity.dto;

import com.example.eBImission.entity.Cart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String cartSn;
    private String trNo;
    private String lrtrNo;
    private String spdNo;
    private String sitmNo;
    private String mbNo;
    private int odQty;
    private String returnCode;

    public Cart toCart() {
        Cart cart = new Cart();
        cart.setTrNo(this.trNo);
        cart.setLrtrNo(this.lrtrNo);
        cart.setSpdNo(this.spdNo);
        cart.setSitmNo(this.sitmNo);
        cart.setMbNo(this.mbNo);
        cart.setOdQty(this.odQty);

        return cart;
    }
}
