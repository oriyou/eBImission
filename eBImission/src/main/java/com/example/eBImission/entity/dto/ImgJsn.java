package com.example.eBImission.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgJsn {

    private String epsrTypCd;
    private String epsrTypDtlCd;
    private String origImgFileNm;
    private String imgFileNm;
    private String imgRteNm;
    private String imgCrtYn;
    private String rprtImgYn;
}
