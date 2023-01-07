package com.elibrary.rshelfstore.service;

import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.ClientPojo;
import com.elibrary.rshelfstore.pojos.OrderPojo;

public interface OrderService {
	
	ClientPojo updateAddressDetails(CartPojo cartPojo);
	CartPojo updateCartDetails(CartPojo cartPojo);
	OrderPojo convertCartToOrder(CartPojo cartPojo);

}
