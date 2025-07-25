package com.emresahin.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emresahin.controller.IUserController;
import com.emresahin.controller.RestBaseController;
import com.emresahin.controller.RootEntity;
import com.emresahin.dto.DtoUser;
import com.emresahin.service.IUserService;

@RestController
@RequestMapping("/user")
public class RestUserControllerImpl extends RestBaseController implements IUserController{
	
	@Autowired
	private IUserService userService;

	@Override
	@GetMapping(path = "/list")
	public List<DtoUser> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	@GetMapping(path = "/list/{id}")
	public DtoUser getStudentById(@PathVariable(name = "id") Long id) {
		return userService.getUserById(id);
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public RootEntity<String> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return RootEntity.ok("Kullanıcı başarıyla silindi. ID: " + id);
	}

}
