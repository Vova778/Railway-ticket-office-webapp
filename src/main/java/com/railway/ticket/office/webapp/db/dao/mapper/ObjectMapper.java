package com.railway.ticket.office.webapp.db.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ObjectMapper<T> {

        T extractFromResultSet(ResultSet resultSet) throws SQLException;

        T toUnique(Map<String, T> cache, T object);

}
