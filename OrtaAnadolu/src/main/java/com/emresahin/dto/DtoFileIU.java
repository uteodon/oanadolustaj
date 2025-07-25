package com.emresahin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoFileIU {
	
	@NotEmpty
    private String fileName;
	
	@NotNull
    private Long userId;
}
