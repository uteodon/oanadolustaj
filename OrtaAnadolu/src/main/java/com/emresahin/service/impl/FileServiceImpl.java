package com.emresahin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emresahin.dto.DtoFile;
import com.emresahin.dto.DtoUser;
import com.emresahin.entity.File;
import com.emresahin.exception.BaseException;
import com.emresahin.exception.ErrorMessage;
import com.emresahin.exception.MessageType;
import com.emresahin.repository.FileRepository;
import com.emresahin.service.IFileService;

@Service
public class FileServiceImpl implements IFileService{
	
	@Autowired 
	private FileRepository fileRepository;

	@Override
	public List<DtoFile> getAllFiles() {
		
		List<DtoFile> dtoFileList = new ArrayList<>();
		List<File> fileList =fileRepository.findAllFiles();
		
		for (File file : fileList) {
			
			DtoFile dtoFile = new DtoFile();
			BeanUtils.copyProperties(file, dtoFile);
			
			dtoFileList.add(dtoFile);
		}
		return dtoFileList;
	}

	@Override
	public DtoFile getFileById(Long id) {
	    DtoFile dtoFile = new DtoFile();
	    Optional<File> optional = fileRepository.findById(id);
	    if(optional.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.FILE_NOT_FOUND, id.toString()));
	    }
	    File dbFile = optional.get();
	    BeanUtils.copyProperties(dbFile, dtoFile);

	    if(dbFile.getUser() != null) {
	        DtoUser dtoUser = new DtoUser();
	        BeanUtils.copyProperties(dbFile.getUser(), dtoUser);
	        dtoFile.setUser(dtoUser);
	    }

	    return dtoFile;
	}

	@Override
	public void deleteFile(Long id) {
		
		Optional<File> optFile = fileRepository.findById(id);
		
		if(optFile.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.FILE_NOT_FOUND, id.toString()));
		}
		
		fileRepository.deleteById(id);
		
	}

}
