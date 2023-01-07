package com.elibrary.rshelfstore.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordModel {
	
	private String email;
    private String oldPassword;
    private String newPassword;

}
