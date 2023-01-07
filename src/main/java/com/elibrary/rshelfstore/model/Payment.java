package com.elibrary.rshelfstore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "os_paymenttable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentDbId;

	private String paymentId;
	private String paymentSignature;
	private String paymentStatus;

	@OneToOne
	private Order order;

	private Date createdOn = new Date();
	private Date lastModifiedOn = new Date();
	private Integer isActive = 1;
	private Integer isDelete = 0;

}
