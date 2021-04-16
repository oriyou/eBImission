package com.example.eBImission.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartProductDto {
    private int cartSn;
    private String trNo;
    private String spdNm;
    private String brdNm;
    private String slPrc;
    private String estmtDlvTxt;
    private int odQty;
    private String spdNo;
    private String sitmNo;
    private String mbNo;
    private ImgJsn[] imgJsn;
}
