package com.elibrary.rshelfstore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "os_inquirytable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JoinColumn(name = "inquiry_id")
	private Long inquiryId;
	
	@OneToOne
	private Book book;
	
	private String inquiryType = "Purchase";
	private String rentDuration;
	private Double offerPrice = 0.0;
	private Double netAmount = 0.0;
	private Integer qty = 1;
	private String status = "Inquiry";
	private Integer isDelete = 0;
	private Integer isActive = 1;
	private Integer isModified = 0;
	private Date createdOn = new Date();
	private Date lastModifiedOn = new Date();

}
