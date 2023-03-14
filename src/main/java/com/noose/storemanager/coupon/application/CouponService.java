package com.noose.storemanager.coupon.application;

import com.noose.storemanager.coupon.application.dto.request.CouponRequest;
import com.noose.storemanager.coupon.application.dto.response.CouponResponse;
import com.noose.storemanager.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponService {
    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponse create(CouponRequest request) {
        // TODO: 쿠폰 생성 후 응답 반환
        return new CouponResponse(1L, "name", "desc");
    }

    public CouponResponse find(Long couponId) {
        // TODO: 쿠폰 조회 후 응답 반환
        return new CouponResponse(1L, "name", "desc");
    }

    public List<CouponResponse> findAll() {
        // TODO: 쿠폰 조회 후 응답 반환
        return Collections.emptyList();
    }

    @Transactional
    public void delete(Long couponId) {
        // TODO: 쿠폰 삭제
    }
}
