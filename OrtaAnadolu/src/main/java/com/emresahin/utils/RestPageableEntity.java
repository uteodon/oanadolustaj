package com.emresahin.utils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestPageableEntity<T> {
	
	private List<T> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private long totalElements;

}