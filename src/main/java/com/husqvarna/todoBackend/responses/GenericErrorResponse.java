package com.husqvarna.todoBackend.responses;

public class GenericErrorResponse {

    private Integer httpStatus;

    private String excMessage;

    public GenericErrorResponse(Integer httpStatus, String excMessage) {
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
