package com.personalsandbox.workmanagement.application;

public class InvalidWorkHoursImportException extends RuntimeException {

    public InvalidWorkHoursImportException(String message) {
        super(message);
    }

    public InvalidWorkHoursImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
