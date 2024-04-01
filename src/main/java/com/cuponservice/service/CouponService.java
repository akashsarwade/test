package com.cuponservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cuponservice.mode.Coupon;
import com.cuponservice.repository.CouponRepo;

@Service
public class CouponService {

	@Autowired
	CouponRepo repo;
	
	public Coupon save(Coupon coupon)
	{
		return repo.save(coupon);
		
	}

	public Coupon getCoupon(String code) {
		return repo.findByCode(code);
	}

	public Coupon create(Coupon coupon)
	{
		return repo.save(coupon);
		
	}
	

}
