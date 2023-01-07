package com.elibrary.rshelfstore.pojos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPojo {

	private Long orderId;
	private Long orderNo;
	private CartPojo cartPojo;
	private String orderstatus;
	private Date createdOn;
	private Date lastModifiedOn;
	private Boolean isDelete;
	private Boolean isActive;
}
