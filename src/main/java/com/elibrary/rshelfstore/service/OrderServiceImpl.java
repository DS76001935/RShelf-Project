package com.elibrary.rshelfstore.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Cart;
import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.model.Order;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.ClientPojo;
import com.elibrary.rshelfstore.pojos.OrderPojo;
import com.elibrary.rshelfstore.repository.CartRepository;
import com.elibrary.rshelfstore.repository.ClientRepository;
import com.elibrary.rshelfstore.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private DtoToDomainClass dtoToDomainObj;

	@Autowired
	private DomainToDtoClass domainToDtoObj;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public ClientPojo updateAddressDetails(CartPojo cartPojo) {
		Client c = null;
		if (cartPojo.getClientpojo().getClientId() != null) {
			cartPojo.getClientpojo().setLastModifiedOn(new Date());

			c = dtoToDomainObj.dtoToClientDomain(cartPojo.getClientpojo());
			c = clientRepo.save(c);
		}
		return domainToDtoObj.domainToClientDto(c);
	}

	@Override
	public CartPojo updateCartDetails(CartPojo cartPojo) {
		Cart obj = null;
		if (cartPojo.getCartId() != null && cartPojo.getInquiries().size() > 0) {
			cartPojo.setLastModifiedOn(new Date());

			obj = dtoToDomainObj.dtoToCartDomain(cartPojo);

			obj = cartRepo.save(obj);
		}
		return domainToDtoObj.domainToCartDto(obj);
	}

	@Override
	public OrderPojo convertCartToOrder(CartPojo cartPojo) {

		OrderPojo myObj = new OrderPojo();

		myObj.setOrderNo(Long.parseLong(String.valueOf(Math.floor(Math.random() * 6))));
		myObj.setCreatedOn(new Date());
		myObj.setLastModifiedOn(new Date());
		myObj.setIsActive(true);
		myObj.setIsDelete(false);
		myObj.setOrderstatus("Confirmed");
		myObj.setCartPojo(cartPojo);

		Order order = dtoToDomainObj.dtoToOrderDomain(myObj);

		order = orderRepository.save(order);

		return domainToDtoObj.domainToOrderDto(order);
	}

}
