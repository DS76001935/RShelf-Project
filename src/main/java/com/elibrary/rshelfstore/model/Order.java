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
@Table(name = "os_ordertable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private Long orderNo;

	@OneToOne
	private Cart cart;
	
	private String orderstatus = "Confirmed";

	private Date createdOn = new Date();
	private Date lastModifiedOn = new Date();
	private Integer isDelete = 0;
	private Integer isActive = 1;

}
