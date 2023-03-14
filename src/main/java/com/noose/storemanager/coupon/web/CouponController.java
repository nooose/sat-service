package com.noose.storemanager.coupon.web;

import com.noose.storemanager.coupon.application.CouponService;
import com.noose.storemanager.coupon.application.dto.request.CouponRequest;
import com.noose.storemanager.coupon.application.dto.response.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest request) {
        CouponResponse coupon = couponService.create(request);
        return ResponseEntity.created(URI.create("/coupons/" + coupon.id())).body(coupon);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<CouponResponse>> findAllCoupon() {
        return ResponseEntity.ok(couponService.findAll());
    }

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResponse> findCoupon(@PathVariable Long couponId) {
        return ResponseEntity.ok(couponService.find(couponId));
    }

    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.delete(couponId);
        return ResponseEntity.noContent().build();
    }
}
