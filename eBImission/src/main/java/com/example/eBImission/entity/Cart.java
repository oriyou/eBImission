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
    private int cartSn;
    @Column("tr_no")
    private String trNo;
    @Column("lrtr_no")
    private String lrtrNo;
    @Column("mb_no")
    private String mbNo;
    @Column("spd_no")
    private String spdNo;
    @Column("sitm_no")
    private String sitmNo;
    @Column("od_qty")
    private int odQty;
    @Column("reg_dttm")
    private LocalDateTime regDttm;
    @Column("mod_dttm")
    private LocalDateTime modDttm;

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
