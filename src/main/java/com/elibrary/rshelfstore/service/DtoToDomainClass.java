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
public class DtoToDomainClass {

	public Cart dtoToCartDomain(CartPojo cartPojo) {
		Cart c = new Cart();

		c.setCartId(cartPojo.getCartId());
		c.setClient(this.dtoToClientDomain(cartPojo.getClientpojo()));
		c.setCreatedOn(cartPojo.getCreatedOn());
		c.setLastModifiedOn(cartPojo.getLastModifiedOn());
		c.setDeliveryChargeStaus(cartPojo.getDeliveryChargeStaus());
		c.setExtraCharges(cartPojo.getExtraCharges());
		c.setFinalAmount(cartPojo.getFinalAmount());
		c.setInquiries(this.dtoToInquiryDomain(cartPojo.getInquiries()));
		c.setIsActive(cartPojo.getIsActive());
		c.setIsDelete(cartPojo.getIsDelete());
		c.setTotalPrice(cartPojo.getTotalPrice());

		return c;
	}

	public Client dtoToClientDomain(ClientPojo clientPojo) {
		Client user = new Client();

		user.setClientId(clientPojo.getClientId());
		user.setFirstName(clientPojo.getFirstName());
		user.setLastName(clientPojo.getLastName());
		user.setPhone(clientPojo.getPhone());
		user.setEmail(clientPojo.getEmail());
		user.setCreatedOn(clientPojo.getCreatedOn());
		user.setLastModifiedOn(clientPojo.getLastModifiedOn());
		user.setAge(clientPojo.getAge());
		user.setClientAddress(clientPojo.getClientAddress());
		user.setDob(clientPojo.getDob());
		user.setIsActive(clientPojo.getIsActive());
		user.setIsDelete(clientPojo.getIsDelete());
		user.setUsername(clientPojo.getUsername());
		user.setPassword(clientPojo.getPassword());
		user.setRole(clientPojo.getRole());

		return user;
	}

	public Set<Inquiry> dtoToInquiryDomain(Set<InquiryPojo> inquiryList) {
		Set<Inquiry> inquiries = new HashSet<Inquiry>();

		for (InquiryPojo item : inquiryList) {
			Inquiry myInquiry = new Inquiry();

			myInquiry.setInquiryId(item.getInquiryId());
			myInquiry.setBook(this.dtoToBookDomain(item.getBookPojo()));
			myInquiry.setCreatedOn(item.getCreatedOn());
			myInquiry.setLastModifiedOn(item.getLastModifiedOn());
			myInquiry.setInquiryType(item.getInquiryType());
			myInquiry.setIsActive(item.getIsActive());
			myInquiry.setIsDelete(item.getIsActive());
			myInquiry.setIsModified(item.getIsModified() ? 1 : 0);
			myInquiry.setNetAmount(item.getNetAmount());
			myInquiry.setOfferPrice(item.getOfferPrice());
			myInquiry.setQty(item.getQty());
			myInquiry.setRentDuration(item.getRentDuration());
			myInquiry.setStatus(item.getStatus());

			inquiries.add(myInquiry);
		}
		return inquiries;
	}

	public Book dtoToBookDomain(BookPojo bookPojo) {
		Book book = new Book();

		book.setBookId(bookPojo.getBookId());
		book.setBookName(bookPojo.getBookName());
		book.setAuthorName(bookPojo.getAuthorName());
		book.setPublicationName(bookPojo.getPublicationName());
		book.setCreatedOn(bookPojo.getCreatedOn());
		book.setLastModifiedOn(bookPojo.getLastModifiedOn());
		book.setIsActive(bookPojo.getIsActive());
		book.setIsDelete(bookPojo.getIsDelete());
		book.setIsbnNo(bookPojo.getIsbnNo());
		book.setDiscount(bookPojo.getDiscount());
		book.setPrice(bookPojo.getPrice());
		book.setQuantity(bookPojo.getQuantity());
		book.setImgUrl(bookPojo.getImgUrl());
		book.setPages(bookPojo.getPages());

		return book;
	}

	public Order dtoToOrderDomain(OrderPojo orderPojo) {
		Order order = new Order();

		order.setCart(dtoToCartDomain(orderPojo.getCartPojo()));
		order.setOrderNo(orderPojo.getOrderNo());
		order.setCreatedOn(orderPojo.getCreatedOn());
		order.setLastModifiedOn(orderPojo.getLastModifiedOn());
		order.setIsActive(orderPojo.getIsActive() ? 1 : 0);
		order.setIsDelete(orderPojo.getIsDelete() ? 1 : 0);
		order.setOrderstatus(orderPojo.getOrderstatus());

		return order;
	}

}
