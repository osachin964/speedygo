package com.stackroute.registrationservice.exception;

import java.time.LocalDateTime;

public class ErrorInfo {

    private String errorMessage;
    private String httpStatus;
    private LocalDateTime localDateTime;

    public ErrorInfo(String errorMessage, String httpStatus, LocalDateTime localDateTime) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

    public ErrorInfo() {
        super();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
