package com.elibrary.rshelfstore.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "os_booktable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {
	
	private static final long serialVersionUID = 6164673665007759289L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;
	
	private String bookName;
	private Long isbnNo;
	private String authorName;
	private String publicationName;
	private Integer pages;
	private Double discount = 0.0d;
	private Integer isActive = 1;
	private Integer isDelete = 0;
	private Double price = 0.0;
	private Long quantity = 0L;
	private String imgUrl;
	private Date createdOn = new Date();
	private Date lastModifiedOn = new Date();
	
}
