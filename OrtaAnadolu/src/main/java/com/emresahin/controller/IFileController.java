package com.emresahin.controller;

import java.util.List;

import com.emresahin.dto.DtoFile;

public interface IFileController {

	public List<DtoFile> getAllFiles();
	
	public DtoFile getFileById(Long id);
	
	public RootEntity<String> deleteFile(Long id);
}
