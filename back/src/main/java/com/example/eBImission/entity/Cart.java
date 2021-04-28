package com.example.eBImission.entity;

import com.example.eBImission.entity.dto.CartProductInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_cart")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {

    @Id
    @Column("cart_sn")
    private String cartSn;     // 장바구니 순번
    @Column("tr_no")
    private String trNo;    // 거래처번호 (그룹 기준)
    @Column("lrtr_no")
    private String lrtrNo;  // 하위거래처번호호
    @Column("mb_no")
    private String mbNo;    // 회원번호
    @Column("spd_no")
    private String spdNo;   // 상품번호
    @Column("sitm_no")
    private String sitmNo;  // 단품번호
    @Column("od_qty")
    private int odQty;      // 주문수량
    @Column("reg_dttm")
    @CreatedDate
    private LocalDateTime regDttm;  // 등록날짜
    @Column("mod_dttm")
    @LastModifiedDate
    private LocalDateTime modDttm;  // 수정날짜

    public Cart(String lrtrNo, String spdNo, String sitmNo) {
        this.lrtrNo = lrtrNo;
        this.spdNo = spdNo;
        this.sitmNo = sitmNo;
    }

    public CartProductInfoDto toDto() {
        CartProductInfoDto dto = new CartProductInfoDto();
        dto.setCartSn(this.cartSn);
        dto.setSpdNo(this.spdNo);
        dto.setSitmNo(this.sitmNo);
        dto.setTrNo(this.trNo);
        dto.setMbNo(this.mbNo);
        dto.setOdQty(this.odQty);
        dto.setRegDttm(this.regDttm);

        return dto;
    }

}
