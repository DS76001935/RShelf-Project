package com.elibrary.rshelfstore.event;

import org.springframework.context.ApplicationEvent;
import com.elibrary.rshelfstore.pojos.CartPojo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCompleteEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -1005142135012948574L;
	
	private CartPojo cartObj;
	private String appUrl;
	
	public SubscriptionCompleteEvent(CartPojo cartPojo, String applicationUrl) {
		super(cartPojo);
		this.cartObj = cartPojo;
		this.appUrl = applicationUrl;
	}

}
