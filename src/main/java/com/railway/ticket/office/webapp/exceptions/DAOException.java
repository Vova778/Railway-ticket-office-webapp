package com.railway.ticket.office.webapp.exceptions;

import java.sql.SQLException;

public class DAOException extends SQLException {

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
