package com.noose.storemanager.domain.user;

import com.noose.storemanager.domain.type.CouponStatus;
import com.noose.storemanager.domain.type.CouponType;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COUPON")
@NoArgsConstructor
public class CouponEntity {
    @Id @GeneratedValue
    @Column(name = "COUPON_ID")
    private long id;

    private int point;
    private int tryCount;

    @Enumerated(EnumType.STRING)
    private CouponType type;
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    public void redeem() {
    }
    public CouponEntity(CouponType type) {
        this.type = type;
        this.status = CouponStatus.IN_USE;
    }
}
