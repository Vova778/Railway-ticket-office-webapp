package com.railway.ticket.office.webapp.utils.db;

import com.railway.ticket.office.webapp.exceptions.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    public static final String MYSQL_PROPS_PATH = "/mysql/db.properties";
    private DBUtils() {}
    public static void rollback(Connection connection) throws DAOException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }
}
