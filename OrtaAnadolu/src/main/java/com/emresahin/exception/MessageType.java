package com.emresahin.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST("kayıt bulunamadı", "1001"),
	IS_TOKEN_EXPIRE("token süresi dolmuştur", "1002"),
	USERNAME_NOT_FOUND("kullanıcı bulunamadı","1003"),
	USERNAME_OR_PASSWORD_INVALID("kullanıcı adı veya şifre hatalı", "1004"),
	REFRESH_TOKEN_NOT_FOUND("refresh token bulunamadı", "1005"),
	REFRESH_TOKEN_IS_EXPIRED("refresh token süresi bitmiştir", "1006"),
	FILE_NOT_SELECTED("dosya seçilmedi", "1007"),
	FILE_NOT_FOUND("Girilen id değeri ile eşleşen dosya bulunamadı", "1008"),
	GENERAL_EXCEPTION("Genel bir hata oluştu", "9999");
	
	private String message;
	
	private String code;
	
	private MessageType(String message, String code) {
		this.message = message;
		this.code = code;
	}
	

}
