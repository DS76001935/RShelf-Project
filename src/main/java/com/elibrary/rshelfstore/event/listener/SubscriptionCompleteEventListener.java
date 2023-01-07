package com.elibrary.rshelfstore.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import com.elibrary.rshelfstore.event.SubscriptionCompleteEvent;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriptionCompleteEventListener implements ApplicationListener<SubscriptionCompleteEvent> {

	@Autowired
	private CartService cartService;

	@Override
	public void onApplicationEvent(SubscriptionCompleteEvent event) {
		// Create the Verification Token for the User with Link
		CartPojo cartPojo = event.getCartObj();
		String token = UUID.randomUUID().toString();
		cartService.saveVerificationToken(token, cartPojo);
		System.out.println("Saved to cart_verification table...");
		// Send Mail to user
		String url = event.getAppUrl() + "/rshelfstore/cart/verifySubscription?token=" + token;

		// sendVerificationEmail()
		log.info("Click the link to verify your account: {}", url);
	}

}
