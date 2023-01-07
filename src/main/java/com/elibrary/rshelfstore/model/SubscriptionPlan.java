package com.elibrary.rshelfstore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "os_subscriptiontable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscriptionId;
	private String planName;
	private String duration;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiresIn;
	private Date startsOn;
	private Integer isDelete = 0;
	private Integer isActive = 1;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdOn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifiedOn;

}
