package com.emresahin.service;

import java.util.List;

import com.emresahin.dto.DtoFile;

public interface IFileService {
	
	public List<DtoFile> getAllFiles();
	
	public DtoFile getFileById(Long id);
	
	public void deleteFile(Long id);

}
