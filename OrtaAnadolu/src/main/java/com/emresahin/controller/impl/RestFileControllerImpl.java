package com.emresahin.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emresahin.controller.IFileController;
import com.emresahin.controller.RestBaseController;
import com.emresahin.controller.RootEntity;
import com.emresahin.dto.DtoFile;
import com.emresahin.service.IFileService;

@RestController
@RequestMapping("/file")
public class RestFileControllerImpl extends RestBaseController implements IFileController{
	
	@Autowired
	private IFileService fileService;

	@Override
	@GetMapping("/list")
	public List<DtoFile> getAllFiles() {
		return fileService.getAllFiles();
	}

	@Override
	@GetMapping("/list/{id}")
	public DtoFile getFileById(@PathVariable(name = "id") Long id) {
		return fileService.getFileById(id);
	}

	@DeleteMapping("/delete/{id}")
	@Override
	public RootEntity<String> deleteFile(@PathVariable Long id) {
	    fileService.deleteFile(id);
	    return RootEntity.ok("Dosya başarıyla silindi. ID: " + id);
	}

}
