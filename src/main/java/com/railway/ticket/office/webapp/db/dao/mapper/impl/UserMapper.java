package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        User user = User.newBuilder()
                .setId(resultSet.getInt(Fields.USER_ID))
                .setLogin(resultSet.getString(Fields.USER_LOGIN))
                .setPassword(  resultSet.getString(Fields.USER_PASSWORD))
                .setFirstName(resultSet.getString(Fields.USER_FIRST_NAME))
                .setLastName(resultSet.getString(Fields.USER_LAST_NAME))
                .setPhone(resultSet.getString(Fields.USER_PHONE))
                .setRole(User.Role.values()[resultSet.getInt(Fields.USER_ROLE_ID)-1])
                .build();
        return user;
    }

    @Override
    public User toUnique(Map<String, User> cache, User object) {
        return null;
    }
}
