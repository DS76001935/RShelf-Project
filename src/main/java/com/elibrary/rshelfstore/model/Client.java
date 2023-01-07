package com.elibrary.rshelfstore.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import com.elibrary.rshelfstore.pojos.ClientAddress;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "client_id")
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
	
	private Integer isActive = 0;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdOn = new Date();
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastModifiedOn = new Date();
	private Integer isDelete = 0;
	private String role = "USER";
}
