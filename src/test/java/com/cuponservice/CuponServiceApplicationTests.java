package com.cuponservice;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@SpringBootTest
@AutoConfigureMockMvc
class CuponServiceApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void testGetCouponWithoutAuth_Forbidden() throws Exception {
		mvc.perform(get("/couponapi/coupons/DIWALI")).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = { "USER" })
	@WithUserDetails("doug@bailey.com")
	public void testGetCouponWithAuth_Success() throws Exception {
		mvc.perform(get("/couponapi/coupons/DIWALI")).andExpect(status().isOk())
				.andExpect((ResultMatcher) content().string("{id:1,code:DIWALI,discount:9999,expDate:}"));

	}

	@Test
	@WithMockUser(roles = { "ADMIN" })
	public void testCreateCoupon_WithoutCSRF_Forbidden() throws Exception {
		mvc.perform(post("couponapi/coupons/")
				.content("{\"code\":\"DIWALIDEC\",\"discount\":9999,\"expDate\":\"30/12/2023\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = { "ADMIN" })
	public void testCreateCoupon_WithCSRF_Forbidden() throws Exception {
		mvc.perform(post("couponapi/coupons/")
				.content("{\"code\":\"DIWALIDEC\",\"discount\":9999,\"expDate\":\"30/12/2023\"}")
				.contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(roles = { "USER" })
	public void testCreateCoupon_NonAdminUser_Forbidden() throws Exception {
		mvc.perform(post("couponapi/coupons/")
				.content("{\"code\":\"DIWALIDEC\",\"discount\":9999,\"expDate\":\"30/12/2023\"}")
				.contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = { "USER" })
	public void testCROS() throws Exception {

		mvc.perform(options("couponapi/coupons").header("Access-Control-Request-Method", "POST").header("Origin",
				"http://www.bharath.com")).andExpect(status().isOk())
				.andExpect(header().exists("Access-Control-Allow-Origin"))
				.andExpect(header().string("Access-Control-Allow-Origin", "*"))
				.andExpect(header().exists("Access-Control-Allow-Methods"))
				.andExpect(header().string("Access-Control-Allow-Methods", "POST"));
	}
}


