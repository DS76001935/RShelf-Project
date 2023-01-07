package com.elibrary.rshelfstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
