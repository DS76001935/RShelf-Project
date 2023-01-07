package com.elibrary.rshelfstore.pojos;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientPojo {
	
	private Long clientId;
	private String firstName;
	private String lastName;
	private Long phone;
	private String email;
	private String username;
	private String password;
	private Integer age;
	
	ClientAddress clientAddress;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dob;
	
	private Integer isActive;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdOn;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifiedOn;
	private Integer isDelete;
	private String role;

}
