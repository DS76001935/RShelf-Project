package com.elibrary.rshelfstore.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elibrary.rshelfstore.model.Cart;
import com.elibrary.rshelfstore.model.CartVerificationToken;
import com.elibrary.rshelfstore.model.Inquiry;
import com.elibrary.rshelfstore.pojos.CartPojo;
import com.elibrary.rshelfstore.pojos.InquiryPojo;
import com.elibrary.rshelfstore.repository.CartRepository;
import com.elibrary.rshelfstore.repository.CartVerificationTokenRepository;
import com.elibrary.rshelfstore.repository.InquiryRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private InquiryRepository inquiryRepository;

	@Autowired
	private CartVerificationTokenRepository cartVerificationTokenRepository;

	@Autowired
	private DomainToDtoClass domainToDtoObj;

	@Autowired
	private DtoToDomainClass dtoToDomainObj;

	@Override
	public CartPojo saveCart(Cart cart) {

		Set<Inquiry> inquiries = new HashSet<Inquiry>();
		Double amt = 0.0;
		Double finalAmt = 0.0;
		if (cart.getCartId() != null) {
			Cart c = null;

			if (cart.getInquiries().size() > 0) {
				c = cartRepository.findById(cart.getCartId()).get();
				if (c != null) {
					cart.getInquiries().forEach((obj) -> obj.setOfferPrice(obj.getBook().getPrice()));
					cart.getInquiries()
							.forEach((item) -> item.setNetAmount(item.getInquiryType().equalsIgnoreCase("Rent")
									? ((item.getBook().getPrice() / 2) * item.getQty())
									: (item.getBook().getPrice() * item.getQty())));

					cart.getInquiries()
							.forEach(
									(item) -> item
											.setNetAmount(
													item.getInquiryType()
															.equalsIgnoreCase("Purchase")
																	? item.getBook().getDiscount() != null
																			? (item.getNetAmount()
																					- (item.getNetAmount()
																							* item.getBook()
																									.getDiscount()
																							/ 100))
																			: (item.getNetAmount() - 0)
																	: (item.getNetAmount() - 0)));

					inquiries = cart.getInquiries().size() > 0 ? cart.getInquiries() : null;
					c.getInquiries().addAll(inquiries);
					for (Inquiry cartItem : c.getInquiries()) {
						amt += cartItem.getNetAmount();
					}
					System.out.println("Total price of already added cart items => " + amt);
					c.getExtraCharges().setCgst(cart.getExtraCharges().getCgst());
					c.getExtraCharges().setSgst(cart.getExtraCharges().getSgst());
					c.getExtraCharges().setDeliveryCharge(cart.getExtraCharges().getDeliveryCharge());
					c.getExtraCharges().setIsDeliveryCostIncluded(cart.getExtraCharges().getIsDeliveryCostIncluded());

					finalAmt = cart.getExtraCharges().getIsDeliveryCostIncluded().equalsIgnoreCase("Yes")
							? amt + (c.getExtraCharges().getCgst() + c.getExtraCharges().getSgst()
									+ c.getExtraCharges().getDeliveryCharge())
							: amt;

					c.setDeliveryChargeStaus(c.getExtraCharges().getIsDeliveryCostIncluded().equalsIgnoreCase("Yes")
							? "Delivery Charges Included"
							: "No Cost Delivery");
					c.setTotalPrice(amt);
					c.setFinalAmount(finalAmt);
					c.setLastModifiedOn(new Date());
				}
			}
			c = cartRepository.save(c);
			return domainToDtoObj.domainToCartDto(c);
		} else {
			cart.getInquiries().forEach((obj) -> obj.setOfferPrice(obj.getBook().getPrice()));
			cart.getInquiries()
					.forEach((item) -> item.setNetAmount(item.getInquiryType().equalsIgnoreCase("Rent")
							? ((item.getBook().getPrice() / 2) * item.getQty())
							: (item.getBook().getPrice() * item.getQty())));

			cart.getInquiries()
					.forEach(
							(item) -> item
									.setNetAmount(
											item.getInquiryType().equalsIgnoreCase("Purchase")
													? item.getBook().getDiscount() != null
															? (item.getNetAmount() - (item.getNetAmount()
																	* item.getBook().getDiscount() / 100))
															: (item.getNetAmount() - 0)
													: (item.getNetAmount() - 0)));

			for (Inquiry item : cart.getInquiries()) {
				amt += item.getNetAmount();
			}
			System.out.println("Total Price of existing cart items => " + amt);
			cart.getExtraCharges().setCgst(cart.getExtraCharges().getCgst());
			cart.getExtraCharges().setSgst(cart.getExtraCharges().getSgst());
			cart.getExtraCharges().setDeliveryCharge(cart.getExtraCharges().getDeliveryCharge());
			cart.getExtraCharges().setIsDeliveryCostIncluded(cart.getExtraCharges().getIsDeliveryCostIncluded());

			finalAmt = cart.getExtraCharges().getIsDeliveryCostIncluded().equalsIgnoreCase("Yes")
					? amt + (cart.getExtraCharges().getCgst() + cart.getExtraCharges().getSgst()
							+ cart.getExtraCharges().getDeliveryCharge())
					: amt;
			cart.setDeliveryChargeStaus(cart.getExtraCharges().getIsDeliveryCostIncluded().equalsIgnoreCase("Yes")
					? "Delivery Charges Included"
					: "No Cost Delivery");
			cart.setTotalPrice(amt);
			cart.setFinalAmount(finalAmt);

			cart = cartRepository.save(cart);
			return domainToDtoObj.domainToCartDto(cart);
		}
	}

	@Override
	public List<CartPojo> getCartById(Long cartId) {

		List<Cart> cartObj = cartRepository.findById(cartId).stream()
				.filter(item -> item.getIsActive() == 1 && item.getIsDelete() == 0).collect(Collectors.toList());

		List<CartPojo> myObj = new ArrayList<CartPojo>();

		for (Cart item : cartObj) {
			myObj.add(this.domainToDtoObj.domainToCartDto(item));
		}

		return myObj;
	}

	@Override
	public List<Inquiry> getInquiryById(Long inquiryId) {
		return inquiryRepository.findById(inquiryId).stream()
				.filter(item -> item.getIsActive() == 1 && item.getIsDelete() == 0).collect(Collectors.toList());
	}

	@Override
	public CartPojo changeInquiry(InquiryPojo inquiryPojo, Long cartId) {
		Cart c = null;
		Inquiry obj = null;
		Double amt = 0.0;
		Double finalAmt = 0.0;
		c = cartRepository.findById(cartId).get();

		for (Inquiry i : c.getInquiries()) {
			if (i.getInquiryId() == inquiryPojo.getInquiryId()) {

				i.setIsModified(1);
				i.setLastModifiedOn(new Date());
				i.setQty(i.getQty() + inquiryPojo.getQty());
				i.setOfferPrice(i.getBook().getPrice());
				i.setNetAmount(i.getBook().getPrice() * i.getQty());
				obj = i;
			}
		}
		c.getInquiries().add(obj);
		System.out.println(c);
		for (Inquiry cartItem : c.getInquiries()) {
			amt += cartItem.getNetAmount();
		}
		System.out.println("Total price of already added cart items => " + amt);

		finalAmt = c.getExtraCharges().getIsDeliveryCostIncluded().equalsIgnoreCase("Yes")
				? amt + (c.getExtraCharges().getCgst() + c.getExtraCharges().getSgst()
						+ c.getExtraCharges().getDeliveryCharge())
				: amt;
		c.setLastModifiedOn(new Date());
		c.setTotalPrice(amt);
		c.setFinalAmount(finalAmt);

		c = cartRepository.save(c);

		CartPojo cartPojo = domainToDtoObj.domainToCartDto(c);
		return cartPojo;
	}

	@Override
	public CartPojo removeInquiry(Long cartId, Long inquiryId) {
		Cart c = cartRepository.findById(cartId).get();
		Inquiry obj = null;
		Double netAmount = 0.0;
		for (Inquiry i : c.getInquiries()) {
			obj = i.getInquiryId() == inquiryId ? i : null;
		}
		if (obj != null) {
			c.getInquiries().remove(obj);
			netAmount = obj.getNetAmount();
			obj.setIsActive(0);
			obj.setIsDelete(1);
			obj.setLastModifiedOn(new Date());
			obj.setIsModified(1);
			c.getInquiries().add(obj);
		}
		c.setLastModifiedOn(new Date());
		c.setTotalPrice(c.getTotalPrice() - netAmount);
		c.setFinalAmount(c.getFinalAmount() - netAmount);

		c = cartRepository.save(c);

		CartPojo cartPojo = domainToDtoObj.domainToCartDto(c);

		return cartPojo;
	}

	@Override
	public CartPojo clearAllInquiries(Long cartId) {
		Cart c = cartRepository.findById(cartId).get();
		Set<Inquiry> inquiries = new HashSet<Inquiry>();
		for (Inquiry obj : c.getInquiries()) {
			obj.setLastModifiedOn(new Date());
			obj.setIsModified(1);
			obj.setIsActive(0);
			obj.setIsDelete(1);
			inquiries.add(obj);
		}
		c.setInquiries(inquiries);
		c.setLastModifiedOn(new Date());
		c.setIsActive(0);
		c.setIsDelete(1);

		c = cartRepository.save(c);

		CartPojo cartPojo = domainToDtoObj.domainToCartDto(c);

		return cartPojo;
	}

	@Override
	public void saveVerificationToken(String token, CartPojo cartPojo) {

		Cart c = dtoToDomainObj.dtoToCartDomain(cartPojo);

		CartVerificationToken cartVerificationToken = new CartVerificationToken(c, token);

		cartVerificationTokenRepository.save(cartVerificationToken);
	}

	@Override
	public String validateCartVerificationToken(String token) {
		CartVerificationToken cartVerificationToken = cartVerificationTokenRepository
				.findCartVerificationTokenByToken(token);

		if (cartVerificationToken == null)
			return "Invalid";

		Cart cart = cartVerificationToken.getCart();
		Calendar cal = Calendar.getInstance();

		if ((cartVerificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
			cartVerificationTokenRepository.delete(cartVerificationToken);
			return "Expired";
		}
		cart.setIsActive(1);
		cartRepository.save(cart);
		return "Verified";
	}

}
