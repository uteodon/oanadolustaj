package com.emresahin.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DtoFile {
	
    private Long id;
    
    private String fileName;
    
    private Date uploadDate;
    
    private DtoUser user;
}
