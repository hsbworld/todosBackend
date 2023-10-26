package com.husqvarna.todoBackend.exceptions;

public class FailedValidationException extends RuntimeException{

    private Integer httpStatus;

    private String excMessage;

    public FailedValidationException(Integer httpStatus, String excMessage) {
        super();
        this.httpStatus = httpStatus;
        this.excMessage = excMessage;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getExcMessage() {
        return excMessage;
    }

    public void setExcMessage(String excMessage) {
        this.excMessage = excMessage;
    }
}
