package com.railway.ticket.officewebapp.db;

import java.sql.SQLException;

public class DBException extends SQLException {

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
