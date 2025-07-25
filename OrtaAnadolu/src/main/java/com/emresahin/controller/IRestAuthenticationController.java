package com.emresahin.controller;

import com.emresahin.dto.AuthRequest;
import com.emresahin.dto.AuthRequest2;
import com.emresahin.dto.AuthResponse;
import com.emresahin.dto.DtoFile;
import com.emresahin.dto.DtoFileIU;
import com.emresahin.dto.DtoLabel;
import com.emresahin.dto.DtoLabelIU;
import com.emresahin.dto.DtoUser;
import com.emresahin.dto.RefreshTokenRequest;
import com.emresahin.utils.RestPageableEntity;
import com.emresahin.utils.RestPageableRequest;
import com.emresahin.utils.RestRootEntity;

public interface IRestAuthenticationController {

	public RootEntity<DtoUser> register(AuthRequest input);
	
	public RootEntity<AuthResponse> authenticate(AuthRequest2 input);
	
	public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest input);
	
	public RootEntity<DtoFile> saveFile(DtoFileIU dtoFileIU);
	
	public RestRootEntity<RestPageableEntity<DtoFile>> listFiles(RestPageableRequest pageableRequest);
	
	public RootEntity<DtoLabel> saveLabel(DtoLabelIU dtoLabelIU);

}
