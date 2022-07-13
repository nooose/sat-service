package com.noose.storemanager.domain.user;

import com.noose.storemanager.domain.type.CouponStatus;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COUPON")
@NoArgsConstructor
public class CouponEntity {
    @Id @GeneratedValue
    @Column(name = "COUPON_ID")
    private long id;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

}
