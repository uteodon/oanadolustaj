package com.emresahin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.emresahin.dto.AuthRequest;
import com.emresahin.dto.AuthRequest2;
import com.emresahin.dto.AuthResponse;
import com.emresahin.dto.DtoFile;
import com.emresahin.dto.DtoFileIU;
import com.emresahin.dto.DtoLabel;
import com.emresahin.dto.DtoLabelIU;
import com.emresahin.dto.DtoUser;
import com.emresahin.dto.RefreshTokenRequest;
import com.emresahin.entity.User;
import com.emresahin.exception.BaseException;
import com.emresahin.exception.ErrorMessage;
import com.emresahin.exception.MessageType;
import com.emresahin.jwt.JWTService;
import com.emresahin.entity.File;
import com.emresahin.entity.Label;
import com.emresahin.entity.RefreshToken;
import com.emresahin.repository.FileRepository;
import com.emresahin.repository.LabelRepository;
import com.emresahin.repository.RefreshTokenRepository;
import com.emresahin.repository.UserRepository;
import com.emresahin.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
    public User createUser(AuthRequest input) {
		
		User user = new User();
		user.setUsername(input.getUsername());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		
		return user;
		
	}
    
	private RefreshToken createRefreshToken(User user) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setUser(user);
		refreshToken.setRefreshToken(UUID.randomUUID().toString());
		refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000*60*60*4));
		
		return refreshToken;
	}

	@Override
	public DtoUser register(AuthRequest input) {
		DtoUser dtoUser = new DtoUser();
		User savedUser = userRepository.save(createUser(input));
		BeanUtils.copyProperties(savedUser, dtoUser);
		return dtoUser;
	}
	
	@Override
	public AuthResponse authenticate(AuthRequest2 input) {
		try {
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
			
			authenticationProvider.authenticate(authenticationToken);
			
			Optional<User> optUser = userRepository.findByUsername(input.getUsername());
			String accessToken = jwtService.generateToken(optUser.get());
			RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));
			
			return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
			
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
		}
		
	}
	
	public boolean isRefreshTokenExpired(Date expireDate) {
		return new Date().before(expireDate);
	}

	@Override
	public AuthResponse refreshToken(RefreshTokenRequest input) {
		Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());
		if(optRefreshToken.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, input.getRefreshToken()));
		}
		if(!isRefreshTokenExpired(optRefreshToken.get().getExpireDate())) {
			throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, input.getRefreshToken()));
		}
		User user = optRefreshToken.get().getUser();
		String accessToken = jwtService.generateToken(user);
		RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));
		return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
	}
 
	public File createFile(DtoFileIU dtoFileIU) {
		
		Optional<User> optUser = userRepository.findById(dtoFileIU.getUserId());
		
		if(optUser.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoFileIU.getUserId().toString()));
		}
		
		File file = new File();
		file.setUploadDate(new Date());
		BeanUtils.copyProperties(dtoFileIU, file);
		
		file.setUser(optUser.get());
		return file;
		
	}
	
   @Override
   public DtoFile saveFile(DtoFileIU dtoFileIU) {
	   
	   DtoFile dtoFile = new DtoFile();
	   DtoUser dtoUser = new DtoUser();
	   
	   File savedFile = fileRepository.save(createFile(dtoFileIU));
	   BeanUtils.copyProperties(savedFile, dtoFile);
	   BeanUtils.copyProperties(savedFile.getUser(),dtoUser);
	   
	   dtoFile.setUser(dtoUser);
	
	return dtoFile;
  }

   @Override
   public Page<File> findAllPageable(Pageable pageable) {
       return fileRepository.findAll(pageable);
   }

   @Override
   public List<DtoFile> toDtoList(List<File> fileList) {
       List<DtoFile> dtoList = new ArrayList<>();
       for (File file : fileList) {
           DtoFile dto = new DtoFile();
           dto.setId(file.getId());
           dto.setFileName(file.getFileName());
           dto.setUploadDate(file.getUploadDate());

           DtoUser dtoUser = new DtoUser();
           dtoUser.setId(file.getUser().getId());
           dtoUser.setUsername(file.getUser().getUsername());
           dtoUser.setEmail(file.getUser().getEmail());           
           dtoUser.setPassword(file.getUser().getPassword());     
           dto.setUser(dtoUser);
           dtoList.add(dto);
       }
       return dtoList;
   }
   
   public Label createLabel(DtoLabelIU dtoLabelIU) {
	   
	   Optional<User> optUser = userRepository.findById(dtoLabelIU.getUserId());
	   if(optUser.isEmpty()) {
		   throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoLabelIU.getUserId().toString()));
	   }
	   
	   Optional<File> optFile = fileRepository.findById(dtoLabelIU.getFileId());
	   if(optFile.isEmpty()) {
		   throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoLabelIU.getFileId().toString()));
	   }
	   
	   Label label = new Label();
	   label.setUser(optUser.get());
	   label.setFile(optFile.get());
	   
	   return label;
   }

   @Override
   public DtoLabel saveLabel(DtoLabelIU dtoLabelIU) {
	
	   DtoLabel dtoLabel = new DtoLabel();
	   DtoFile dtoFile = new DtoFile();
	   DtoUser dtoUser = new DtoUser();
	   DtoUser dtoUser2 = new DtoUser();
	   
	   Label savedLabel = labelRepository.save(createLabel(dtoLabelIU));
	   BeanUtils.copyProperties(savedLabel, dtoLabel);
	   BeanUtils.copyProperties(savedLabel.getFile(), dtoFile);
	   BeanUtils.copyProperties(savedLabel.getFile().getUser(), dtoUser2);
	   BeanUtils.copyProperties(savedLabel.getUser(), dtoUser);
	   
	   dtoFile.setUser(dtoUser2);
	   
	   dtoLabel.setFile(dtoFile);
	   dtoLabel.setUser(dtoUser);
	   
	   
	return dtoLabel;
   }


}
