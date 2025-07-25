package com.emresahin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoLabelIU {

	@NotNull
    private Long userId;
	
	@NotNull
    private Long fileId;
}
