package com.emresahin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.emresahin.dto.AuthRequest;
import com.emresahin.dto.AuthRequest2;
import com.emresahin.dto.AuthResponse;
import com.emresahin.dto.DtoFile;
import com.emresahin.dto.DtoFileIU;
import com.emresahin.dto.DtoLabel;
import com.emresahin.dto.DtoLabelIU;
import com.emresahin.dto.DtoUser;
import com.emresahin.dto.RefreshTokenRequest;
import com.emresahin.entity.File;

public interface IAuthenticationService {
	
	public DtoUser register(AuthRequest input);
	
	public AuthResponse authenticate(AuthRequest2 input);
	
	public AuthResponse refreshToken(RefreshTokenRequest input);
	
	public DtoFile saveFile(DtoFileIU dtoFileIU);

    Page<File> findAllPageable(Pageable pageable);

    List<DtoFile> toDtoList(List<File> fileList);
    
    public DtoLabel saveLabel(DtoLabelIU dtoLabelIU);

}
