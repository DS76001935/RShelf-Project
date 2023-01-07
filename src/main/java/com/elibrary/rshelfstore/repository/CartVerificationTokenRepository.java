package com.elibrary.rshelfstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.CartVerificationToken;

@Repository
public interface CartVerificationTokenRepository extends JpaRepository<CartVerificationToken, Long> {
	
	CartVerificationToken findCartVerificationTokenByToken(String token);

}
