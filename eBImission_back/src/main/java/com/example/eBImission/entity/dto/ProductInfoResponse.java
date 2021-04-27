package com.example.eBImission.entity.dto;

import com.example.eBImission.entity.Cart;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInfoResponse {

    private String returnCode;
    private CartProductInfoDto[] data;

}
