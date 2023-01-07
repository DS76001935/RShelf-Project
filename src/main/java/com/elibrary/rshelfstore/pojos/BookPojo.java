package com.elibrary.rshelfstore.pojos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPojo {
	
private Integer bookId;
	
	private String bookName;
	private Long isbnNo;
	private String authorName;
	private String publicationName;
	private Integer pages;
	private Double discount;
	private Integer isActive;
	private Integer isDelete;
	private Double price;
	private Long quantity;
	private String imgUrl;
	private Date createdOn;
	private Date lastModifiedOn;

}
