package com.elibrary.rshelfstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elibrary.rshelfstore.event.SubscriptionCompleteEvent;
import com.elibrary.rshelfstore.model.Cart;
import com.elibrary.rshelfstore.model.Inquiry;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.InquiryPojo;
import com.elibrary.rshelfstore.pojos.ResponseEntity;
import com.elibrary.rshelfstore.repository.InquiryRepository;
import com.elibrary.rshelfstore.service.CartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rshelfstore/cart")
@CrossOrigin(origins = "*")
@Slf4j
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private InquiryRepository inquiryRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping("/insertInquiry")
	public ResponseEntity<CartPojo> insertCartItem(@RequestBody Cart cart, HttpServletRequest request) {
		ResponseEntity<CartPojo> res = new ResponseEntity<CartPojo>();

		CartPojo cartObj = cartService.saveCart(cart);
		if (cartObj != null) {

			log.info("--- cart Inserted ---" + cartObj);
			Set<InquiryPojo> inquiryList = cartObj.getInquiries();
			log.info("----Inquiry List---", inquiryList);
			inquiryList.forEach((item) -> {
				try {

					Inquiry inquiryObj = inquiryRepository.findById(item.getInquiryId()).get();
					log.info("--- Inquiry Inserted --- " + inquiryObj);
//					}
				} catch (Exception e) {
					System.out
							.println("Exception while generating cart as well as it's inquiries => " + e.getMessage());
					e.printStackTrace();
				}
			});
			res.setData(cartObj);
			res.setMessage("Your cart is ready with inquiries.");
			res.setStatus(200);

		} else {
			res.setData(null);
			res.setMessage("Something went wrong for inquiry generation!");
			res.setStatus(-1);
		}
		return res;
	}

	@GetMapping("/getCartById")
	public ResponseEntity<List<CartPojo>> getCartById(@RequestParam("cartId") Long cartId) {
		ResponseEntity<List<CartPojo>> res = new ResponseEntity<List<CartPojo>>();
		List<Inquiry> inquiryObj = new ArrayList<Inquiry>();
		List<CartPojo> cartObj = cartService.getCartById(cartId);

		if (cartObj != null && cartObj.size() > 0) {

			for (InquiryPojo i : cartObj.get(0).getInquiries()) {
				inquiryObj.addAll(cartService.getInquiryById(i.getInquiryId()));
			}
			if (inquiryObj == null || inquiryObj.size() == 0) {
				log.info("Cart Found -- No Inquiries!");

				res.setData(null);
				res.setMessage("No Inquiries!");
				res.setStatus(200);
			} else {
				log.info("The cart has been retrieved along with all inquiries.");
				res.setData(cartObj);
				res.setMessage("The cart has been retrieved along with all inquiries.");
				res.setStatus(200);
			}
		} else {
			log.info("Cart Not Found!");

			res.setData(null);
			res.setMessage("Cart Not Found!");
			res.setStatus(200);
		}
		return res;
	}

	@PutMapping("/changeInquiry")
	public ResponseEntity<CartPojo> changeInquiry(@RequestParam("cartId") Long cartId,
			@RequestBody InquiryPojo inquiryPojo) {
		ResponseEntity<CartPojo> res = new ResponseEntity<CartPojo>();

		if (inquiryPojo.getInquiryId() != null) {
			CartPojo cartObj = cartService.changeInquiry(inquiryPojo, cartId);

			if (cartObj != null) {
				log.info("Inquiry Updated");

				res.setData(cartObj);
				res.setMessage("Inquiry Updated");
				res.setStatus(-1);
			} else {
				log.info("Oops, try again later!");

				res.setData(null);
				res.setMessage("Oops, try again later!");
				res.setStatus(-1);
			}
		} else {
			log.info("Invalid Inquiry!");

			res.setData(null);
			res.setMessage("Invalid Inquiry!");
			res.setStatus(-1);
		}

		return res;
	}

	@DeleteMapping("/removeInquiry")
	public ResponseEntity<CartPojo> removeInquiry(@RequestParam("cartId") Long cartId,
			@RequestParam("inquiryId") Long inquiryId) {
		ResponseEntity<CartPojo> res = new ResponseEntity<CartPojo>();

		CartPojo cartObj = cartService.removeInquiry(cartId, inquiryId);

		if (cartObj != null) {
			log.info("Inquiry Removed");

			res.setData(cartObj);
			res.setMessage("Inquiry Removed");
			res.setStatus(-1);
		} else {
			log.info("Oops, try again later!");

			res.setData(null);
			res.setMessage("Oops, try again later!");
			res.setStatus(-1);
		}

		return res;
	}

	@DeleteMapping("clearAllInquiries")
	public ResponseEntity<CartPojo> clearAllInquiries(@RequestParam("cartId") Long cartId) {
		ResponseEntity<CartPojo> res = new ResponseEntity<CartPojo>();
		CartPojo cartObj = cartService.clearAllInquiries(cartId);
		if (cartObj != null) {
			log.info("A cart has been cleared with all inquiries");

			res.setData(cartObj);
			res.setMessage("A cart has been cleared with all inquiries");
			res.setStatus(-1);
		} else {
			log.info("Oops, try again later!");

			res.setData(null);
			res.setMessage("Oops, try again later!");
			res.setStatus(-1);
		}
		return res;
	}

	@PostMapping("/proceedToPay")
	public ResponseEntity<CartPojo> proceedToPay(@RequestBody CartPojo cartObj, HttpServletRequest request) {
		ResponseEntity<CartPojo> res = new ResponseEntity<CartPojo>();

		publisher.publishEvent(new SubscriptionCompleteEvent(cartObj, applicationUrl(request)));

		if (cartObj != null) {
			res.setData(cartObj);
			res.setMessage("Cart has been verified.");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("Something went wrong!");
			res.setStatus(-1);
		}

		return res;
	}

	@GetMapping("/verifySubscription")
	public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
		ResponseEntity<String> res = new ResponseEntity<String>();
		String result = cartService.validateCartVerificationToken(token);
		if (result.equalsIgnoreCase("Verified")) {
			res.setMessage("Cart Verified Successfully");
			return res;
		}
		res.setMessage("Invaid Infomation has been wrongly Parsed!");
		return res;
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
