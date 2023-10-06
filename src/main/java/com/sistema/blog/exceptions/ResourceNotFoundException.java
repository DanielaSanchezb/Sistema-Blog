package com.sistema.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String textFieldName;
    private long  textFieldValue;

    public ResourceNotFoundException(String resourceName, String textFieldName, long textFieldValue){
        super(String.format("%s not found with : %s : '%s'",resourceName,textFieldName,textFieldValue));
        this.resourceName = resourceName;
        this.textFieldName = textFieldName;
        this.textFieldValue = textFieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTextFieldName() {
        return textFieldName;
    }

    public void setTextFieldName(String textFieldName) {
        this.textFieldName = textFieldName;
    }

    public long getTextFieldValue() {
        return textFieldValue;
    }

    public void setTextFieldValue(long textFieldValue) {
        this.textFieldValue = textFieldValue;
    }
}
