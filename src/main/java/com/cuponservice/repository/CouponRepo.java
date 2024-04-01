package com.cuponservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuponservice.mode.Coupon;

public interface CouponRepo  extends JpaRepository<Coupon, Long>{

	Coupon findByCode(String code);

}
