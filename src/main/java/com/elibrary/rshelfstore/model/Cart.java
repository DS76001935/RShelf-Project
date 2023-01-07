package com.elibrary.rshelfstore.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.elibrary.rshelfstore.pojos.ExtraCharges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "os_carttable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	private Integer isDelete = 0;
	private Integer isActive = 0;
	private Date createdOn = new Date();
	private Date lastModifiedOn = new Date();
	
	@OneToOne
	private Client client;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<Inquiry> inquiries;
	
	private Double totalPrice = 0.0;
	private ExtraCharges extraCharges;
	private String deliveryChargeStaus;
	private Double finalAmount = 0.0;
}
