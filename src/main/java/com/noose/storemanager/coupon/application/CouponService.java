package com.noose.storemanager.coupon.application;

import com.noose.storemanager.coupon.application.dto.request.CouponRequest;
import com.noose.storemanager.coupon.application.dto.response.CouponResponse;
import com.noose.storemanager.coupon.domain.Coupon;
import com.noose.storemanager.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {
    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponse create(CouponRequest request) {
        Coupon newCoupon = new Coupon(request.name(), request.description());
        Coupon coupon = couponRepository.save(newCoupon);
        return CouponResponse.of(coupon);
    }

    public CouponResponse find(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalStateException("존재하지 않는 쿠폰번호 입니다."));
        return CouponResponse.of(coupon);
    }

    public List<CouponResponse> findAll() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream().map(CouponResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long couponId) {
        CouponResponse couponResponse = find(couponId);
        couponRepository.deleteById(couponResponse.id());
    }
}
