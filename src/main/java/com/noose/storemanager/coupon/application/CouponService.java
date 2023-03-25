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
        Coupon coupon = new Coupon(request.name(), request.description());
        couponRepository.save(coupon);
        return CouponResponse.of(coupon);
    }

    public CouponResponse find(Long couponId) {
        return CouponResponse.of(findCoupon(couponId));
    }

    public List<CouponResponse> findAll() {
        return couponRepository.findAll()
                .stream()
                .map(CouponResponse::of)
                .toList();
    }

    @Transactional
    public void delete(Long couponId) {
        findCoupon(couponId);
        couponRepository.deleteById(couponId);
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalStateException(String.format("%d번 쿠폰은 존재하지 않습니다.", couponId)));
    }
}
