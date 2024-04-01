package com.cuponservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuponservice.mode.Coupon;
import com.cuponservice.service.CouponService;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

	@Autowired
	CouponService serv;

	@PostMapping("/coupons")
	@PreAuthorize("hasRole('ADMIN')")
	public Coupon create(@RequestBody Coupon coupon) {
		return serv.create(coupon);
	}

	@GetMapping("/coupons/{code}")
//	@PostAuthorize("returnObject.discount<6000")
//	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public Coupon getCoupon(@PathVariable("code") String code) {
		return serv.getCoupon(code);

	}
}
