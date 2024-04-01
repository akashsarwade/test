package com.cuponservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cuponservice.mode.Coupon;
import com.cuponservice.service.CouponService;

import jakarta.annotation.security.RolesAllowed;

@Controller

public class CouponController {

	@Autowired
	private CouponService serv;

	@GetMapping("/showCreateCoupon")
//	@PreAuthorize("hasRole('ADMIN')")
//	@RolesAllowed("ADMIN")
//	@Secured("ADMIN")
	public String showCreateCoupon() {
		return "createCoupon";
	}

	@PostMapping("/saveCoupon")
	public String save(Coupon coupon) {
		serv.save(coupon);
		return "createResponse";
	}

	@GetMapping("/showGetCoupon")
	public String showGetCoupon() {
		return "getCoupon";
	}

	@PostMapping("/getCoupon")
	public ModelAndView getCoupon(String code) {
		ModelAndView mav = new ModelAndView("couponDetails");
		System.out.println(code);
		mav.addObject(serv.getCoupon(code));
		return mav;
	}

}
