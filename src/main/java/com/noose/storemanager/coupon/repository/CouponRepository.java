package com.noose.storemanager.coupon.repository;

import com.noose.storemanager.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
