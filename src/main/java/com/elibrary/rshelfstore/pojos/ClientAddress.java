package com.elibrary.rshelfstore.pojos;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddress {
	
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private Long pincode;
}
