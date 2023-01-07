package com.elibrary.rshelfstore.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Book;
import com.elibrary.rshelfstore.model.Cart;
import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.model.Inquiry;
import com.elibrary.rshelfstore.model.Order;
import com.elibrary.rshelfstore.pojos.BookPojo;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.ClientPojo;
import com.elibrary.rshelfstore.pojos.InquiryPojo;
import com.elibrary.rshelfstore.pojos.OrderPojo;

@Service
public class DomainToDtoClass {

	public CartPojo domainToCartDto(Cart cart) {
		CartPojo c = new CartPojo();

		c.setCartId(cart.getCartId());
		c.setClientpojo(this.domainToClientDto(cart.getClient()));
		c.setCreatedOn(cart.getCreatedOn());
		c.setLastModifiedOn(cart.getLastModifiedOn());
		c.setDeliveryChargeStaus(cart.getDeliveryChargeStaus());
		c.setExtraCharges(cart.getExtraCharges());
		c.setFinalAmount(cart.getFinalAmount());
		c.setInquiries(this.domainToInquiryDto(cart.getInquiries()));
		c.setIsActive(cart.getIsActive());
		c.setIsDelete(cart.getIsDelete());
		c.setTotalPrice(cart.getTotalPrice());

		return c;
	}

	public ClientPojo domainToClientDto(Client client) {
		ClientPojo user = new ClientPojo();

		user.setClientId(client.getClientId());
		user.setFirstName(client.getFirstName());
		user.setLastName(client.getLastName());
		user.setPhone(client.getPhone());
		user.setEmail(client.getEmail());
		user.setCreatedOn(client.getCreatedOn());
		user.setLastModifiedOn(client.getLastModifiedOn());
		user.setAge(client.getAge());
		user.setClientAddress(client.getClientAddress());
		user.setDob(client.getDob());
		user.setIsActive(client.getIsActive());
		user.setIsDelete(client.getIsDelete());
		user.setUsername(client.getUsername());
		user.setPassword(client.getPassword());
		user.setRole(client.getRole());

		return user;
	}

	public Set<InquiryPojo> domainToInquiryDto(Set<Inquiry> inquiryList) {
		Set<InquiryPojo> inquiries = new HashSet<InquiryPojo>();

		for (Inquiry item : inquiryList) {
			InquiryPojo myInquiry = new InquiryPojo();

			myInquiry.setInquiryId(item.getInquiryId());
			myInquiry.setBookPojo(this.domainToBookDto(item.getBook()));
			myInquiry.setCreatedOn(item.getCreatedOn());
			myInquiry.setLastModifiedOn(item.getLastModifiedOn());
			myInquiry.setInquiryType(item.getInquiryType());
			myInquiry.setIsActive(item.getIsActive());
			myInquiry.setIsDelete(item.getIsActive());
			myInquiry.setIsModified(item.getIsModified() == 1 ? true : false);
			myInquiry.setNetAmount(item.getNetAmount());
			myInquiry.setOfferPrice(item.getOfferPrice());
			myInquiry.setQty(item.getQty());
			myInquiry.setRentDuration(item.getRentDuration());
			myInquiry.setStatus(item.getStatus());

			inquiries.add(myInquiry);
		}
		return inquiries;
	}

	public BookPojo domainToBookDto(Book book) {
		BookPojo bookPojo = new BookPojo();

		bookPojo.setBookId(book.getBookId());
		bookPojo.setBookName(book.getBookName());
		bookPojo.setAuthorName(book.getAuthorName());
		bookPojo.setPublicationName(book.getPublicationName());
		bookPojo.setCreatedOn(book.getCreatedOn());
		bookPojo.setLastModifiedOn(book.getLastModifiedOn());
		bookPojo.setIsActive(book.getIsActive());
		bookPojo.setIsDelete(book.getIsDelete());
		bookPojo.setIsbnNo(book.getIsbnNo());
		bookPojo.setDiscount(book.getDiscount());
		bookPojo.setPrice(book.getPrice());
		bookPojo.setQuantity(book.getQuantity());
		bookPojo.setImgUrl(book.getImgUrl());
		bookPojo.setPages(book.getPages());

		return bookPojo;
	}

	public OrderPojo domainToOrderDto(Order order) {
		OrderPojo orderPojo = new OrderPojo();

		orderPojo.setOrderId(order.getOrderId());
		orderPojo.setOrderstatus(order.getOrderstatus());
		orderPojo.setOrderNo(order.getOrderNo());
		orderPojo.setCartPojo(this.domainToCartDto(order.getCart()));
		orderPojo.setCreatedOn(order.getCreatedOn());
		orderPojo.setLastModifiedOn(order.getLastModifiedOn());
		orderPojo.setIsActive(order.getIsActive() == 1 ? true : false);
		orderPojo.setIsDelete(order.getIsDelete() == 1 ? true : false);

		return orderPojo;
	}

}
