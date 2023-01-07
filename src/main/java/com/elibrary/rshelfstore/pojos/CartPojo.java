package com.elibrary.rshelfstore.pojos;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartPojo {
	
	private Long cartId;
	private Integer isDelete;
	private Integer isActive;
	private Date createdOn;
	private Date lastModifiedOn;
	private ClientPojo clientpojo;
	private Set<InquiryPojo> inquiries;
	private Double totalPrice;
	private ExtraCharges extraCharges;
	private String deliveryChargeStaus;
	private Double finalAmount;

}
