package com.railway.ticket.office.webapp.exceptions;

public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable exception) {
        super(message, exception);
    }
}
