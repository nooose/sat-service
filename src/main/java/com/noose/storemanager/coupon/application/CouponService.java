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
        // TODO: 쿠폰 생성 후 응답 반환
        Coupon newCoupon = request.of(request);
        Coupon coupon = couponRepository.save(newCoupon);
        return coupon.toResponse();
    }

    public CouponResponse find(Long couponId) {
        // TODO: 쿠폰 조회 후 응답 반환
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalStateException("존재하지 않는 쿠폰번호입니다."));
        return coupon.toResponse();
    }

    public List<CouponResponse> findAll() {
        // TODO: 쿠폰 조회 후 응답 반환
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream().map(Coupon::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long couponId) {
        // TODO: 쿠폰 삭제
        couponRepository.deleteById(couponId);
    }
}
