package com.elibrary.rshelfstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
