package com.elibrary.rshelfstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Inquiry;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
