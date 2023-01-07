package com.elibrary.rshelfstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Inquiry;
import com.elibrary.rshelfstore.repository.InquiryRepository;

@Service
public class InquiryServiceImpl implements InquiryService {
	
	@Autowired
	private InquiryRepository inquiryRepository;
	
	@Override
	public Inquiry insertCartItem(Inquiry inquiry) throws Exception {
		return inquiryRepository.save(inquiry);
	}

}
