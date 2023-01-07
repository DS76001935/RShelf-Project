package com.elibrary.rshelfstore.service;

import java.util.List;

import com.elibrary.rshelfstore.model.Cart;
import com.elibrary.rshelfstore.model.Inquiry;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.InquiryPojo;

public interface CartService {
	
	CartPojo saveCart(Cart cart);
	List<CartPojo> getCartById(Long cartId);
	List<Inquiry> getInquiryById(Long inquiryId);
	CartPojo changeInquiry(InquiryPojo inquiryPojo, Long cartId);
	CartPojo removeInquiry(Long cartId, Long inquiryId);
	CartPojo clearAllInquiries(Long cartId);
	void saveVerificationToken(String token, CartPojo cartPojo);
	String validateCartVerificationToken(String verificationToken);

}
