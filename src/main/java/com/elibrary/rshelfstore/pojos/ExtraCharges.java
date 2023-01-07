package com.elibrary.rshelfstore.pojos;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ExtraCharges {
	
	private Double cgst = 4.0;
	private Double sgst = 5.0;
	private String isDeliveryCostIncluded = "Yes";
	private Double deliveryCharge = 30.0;

}
