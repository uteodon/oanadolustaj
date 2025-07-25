package com.emresahin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest2 {
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;

}
