package com.emresahin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emresahin.dto.DtoUser;
import com.emresahin.entity.User;
import com.emresahin.exception.BaseException;
import com.emresahin.exception.ErrorMessage;
import com.emresahin.exception.MessageType;
import com.emresahin.repository.UserRepository;
import com.emresahin.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<DtoUser> getAllUsers() {
		
		List<DtoUser> dtoUserList = new ArrayList<>();
		List<User> userList = userRepository.findAllUser();
		
		for (User user : userList) {
			
			DtoUser dtoUser = new DtoUser();
			BeanUtils.copyProperties(user, dtoUser);
			
			dtoUserList.add(dtoUser);

		}
		return dtoUserList;
	}

	@Override
	public DtoUser getUserById(Long id) {
		
        DtoUser dtoUser = new DtoUser();
        
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
			return null;
		}
        User dbUser = optional.get();
        BeanUtils.copyProperties(dbUser, dtoUser);
		
		return dtoUser;
	}

	@Override
	public void deleteUser(Long id) {
		
    Optional<User> optUser = userRepository.findById(id);
    if(optUser.isEmpty()) {
    	throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, id.toString()));
    }
    userRepository.deleteById(id);
		
	}

}
