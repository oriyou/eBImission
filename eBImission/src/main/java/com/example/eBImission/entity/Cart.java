package com.example.eBImission.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_cart")
public class Cart{
    @Id
    @Column("cart_sn")
    private int cartSn;     // 장바구니 순번
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
    private LocalDateTime regDttm;  // 등록날짜
    @Column("mod_dttm")
    private LocalDateTime modDttm;  // 수정날짜

//    @Override
//    public String getId() {
//        return this.cartSn;
//    }
//
//    @Override
//    public boolean isNew() {
//        boolean result = Objects.isNull(cartSn);
//        this.cartSn = result ? String.valueOf(UUID.randomUUID()) : this.cartSn;
//        return result;
//    }
}
