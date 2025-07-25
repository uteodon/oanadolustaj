package com.emresahin.service;

import java.util.List;

import com.emresahin.dto.DtoUser;

public interface IUserService {
	
	public List<DtoUser> getAllUsers();
	
	public DtoUser getUserById(Long id);
	
	public void deleteUser(Long id);

}
