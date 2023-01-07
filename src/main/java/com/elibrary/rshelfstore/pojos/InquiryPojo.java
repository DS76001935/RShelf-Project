package com.elibrary.rshelfstore.pojos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryPojo {
	
private Long inquiryId;
	
	private BookPojo bookPojo;
	private String inquiryType;
	private String rentDuration;
	private Double offerPrice;
	private Double netAmount;
	private Integer qty;
	private String status;
	private Integer isDelete;
	private Integer isActive;
	private Boolean isModified;
	private Date createdOn;
	private Date lastModifiedOn;

}
