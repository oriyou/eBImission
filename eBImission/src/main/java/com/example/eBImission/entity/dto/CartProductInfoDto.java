package com.example.eBImission.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
// Json 입력값에 대하여 Mapping 시 클래스에 선언되지 않은 property 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartProductInfoDto {
    private String cartSn;
    private String trNo;
    private String lrtrNo;
    private String spdNm;
    private String brdNm;
    private String slPrc;
    private String estmtDlvTxt;
    private int odQty;
    private String spdNo;
    private String sitmNo;
    private String mbNo;
    private LocalDateTime regDttm;
    private ImgJsn[] imgJsn;
}