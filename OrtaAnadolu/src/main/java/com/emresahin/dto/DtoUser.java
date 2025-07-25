package com.emresahin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUser extends DtoBase{
	
    private String username;
	
	private String password;
	
	private String email;
}