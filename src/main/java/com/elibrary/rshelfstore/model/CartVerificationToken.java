package com.elibrary.rshelfstore.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_verification_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartVerificationToken {

	// Expiration time of 10 minutes
	private static final int EXPIRATION_TIME = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private Long id;
	private String token;
	private Date expirationTime;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cartId", nullable = false, foreignKey = @ForeignKey(name = "FK_CART_VERIFY_TOKEN"))
	Cart cart;

	public CartVerificationToken(Cart cart, String token) {
		super();
		this.token = token;
		this.cart = cart;
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	}

	public CartVerificationToken(String token) {
		super();

		this.token = token;
		this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
	}

	private Date calculateExpirationDate(int expirationTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}

}
