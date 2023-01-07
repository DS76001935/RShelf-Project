package com.elibrary.rshelfstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.ClientPojo;
import com.elibrary.rshelfstore.pojos.OrderPojo;
import com.elibrary.rshelfstore.pojos.ResponseEntity;
import com.elibrary.rshelfstore.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rshelfstore/orders")
@CrossOrigin(origins = "*")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/checkout")
	public ResponseEntity<OrderPojo> convertCartToOrder(@RequestBody CartPojo cartPojo) {

		ResponseEntity<OrderPojo> res = new ResponseEntity<OrderPojo>();

		if (cartPojo != null) {
			ClientPojo myClient = orderService.updateAddressDetails(cartPojo);

			if (!cartPojo.getClientpojo().getLastModifiedOn().equals(myClient.getLastModifiedOn())) {
				CartPojo updatedCart = orderService.updateCartDetails(cartPojo);
				log.info("Your address details has been updated!!");

				OrderPojo orderPojo = orderService.convertCartToOrder(updatedCart);

				res.setMessage("Your address details has been updated!!");
				res.setData(orderPojo);
				res.setStatus(200);
			}
		}

		return res;
	}

}
