package com.emresahin.controller.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emresahin.controller.IRestAuthenticationController;
import com.emresahin.controller.RestBaseController;
import com.emresahin.controller.RootEntity;
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
import com.emresahin.service.IAuthenticationService;
import com.emresahin.utils.PagerUtil;
import com.emresahin.utils.RestPageableEntity;
import com.emresahin.utils.RestPageableRequest;
import com.emresahin.utils.RestRootEntity;

import jakarta.validation.Valid;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController{
	
	@Autowired
	private IAuthenticationService authenticationService;

	@Override
	@PostMapping("/register")
	public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest input) {
		return ok(authenticationService.register(input));
	}
	
	@Override
	@PostMapping("/authenticate")
	public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest2 input) {
		return ok(authenticationService.authenticate(input));
	}

	@Override
	@PostMapping("/refreshToken")
	public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest input) {
		return ok(authenticationService.refreshToken(input));
	}

	@PostMapping("/listFiles")
	public RestRootEntity<RestPageableEntity<DtoFile>> listFiles(@RequestBody RestPageableRequest pageableRequest) {

	    Pageable pageable = PagerUtil.toPageable(pageableRequest);
	    Page<File> page = authenticationService.findAllPageable(pageable);
	    List<DtoFile> dtoList = authenticationService.toDtoList(page.getContent());
	    RestPageableEntity<DtoFile> pageableResponse = PagerUtil.toPageableResponse(page, dtoList);

	    return RestRootEntity.ok(pageableResponse);
	}

	@Override
	@PostMapping("/saveFile")
	public RootEntity<DtoFile> saveFile(@Valid @RequestBody DtoFileIU dtoFileIU) {
		return ok(authenticationService.saveFile(dtoFileIU));
	}

	@Override
	@PostMapping("/saveLabel")
	public RootEntity<DtoLabel> saveLabel(@Valid @RequestBody DtoLabelIU dtoLabelIU) {
		return ok(authenticationService.saveLabel(dtoLabelIU));
	}

}
