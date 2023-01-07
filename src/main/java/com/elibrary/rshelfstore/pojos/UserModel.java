package com.elibrary.rshelfstore.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
	private String username;
	private String newPassword;
	private String oldPassword;

}
