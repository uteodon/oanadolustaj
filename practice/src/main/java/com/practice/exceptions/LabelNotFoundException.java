package com.practice.exceptions;

public class LabelNotFoundException extends RuntimeException{

    public LabelNotFoundException(String message){
        super(message);
    }
}
