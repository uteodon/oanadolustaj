package com.emresahin.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PagerUtil {
	
	public boolean isNullOrEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
	
	public Pageable toPageable(RestPageableRequest pageableRequest) {
		
		if(!isNullOrEmpty(pageableRequest.getColumnName())) {
			Sort sortBy = pageableRequest.isAsc() ? Sort.by(Direction.ASC, pageableRequest.getColumnName())
					: Sort.by(Direction.DESC, pageableRequest.getColumnName());
			
			return PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), sortBy);
		}
		return PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize());
	}
	
	public <T> RestPageableEntity<T> toPageableResponse(Page<?> page, List<T> content) {
		
		RestPageableEntity<T> entity = new RestPageableEntity<T>();
		entity.setContent(content);
		entity.setPageNumber(page.getPageable().getPageNumber());
		entity.setPageSize(page.getPageable().getPageSize());
		entity.setTotalElements(page.getTotalElements());
		
		return entity;
	}

}