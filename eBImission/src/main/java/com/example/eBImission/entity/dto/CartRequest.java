package com.example.eBImission.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    private String spdNo;
    private String sitmNo;
    private String mbNo;
    private int odQty;
}
