package com.emresahin.controller;

import java.util.List;

import com.emresahin.dto.DtoUser;

public interface IUserController {

	public List<DtoUser> getAllUsers();
	
	public DtoUser getStudentById(Long id);
	 
	public RootEntity<String> deleteUser(Long id);
}
