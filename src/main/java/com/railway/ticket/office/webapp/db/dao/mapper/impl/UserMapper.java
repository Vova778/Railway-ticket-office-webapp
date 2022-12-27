package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return User.newBuilder()
                .setId(resultSet.getInt(Fields.USER_ID))
                .setLogin(resultSet.getString(Fields.USER_LOGIN))
                .setPassword(  resultSet.getString(Fields.USER_PASSWORD))
                .setFirstName(resultSet.getString(Fields.USER_FIRST_NAME))
                .setLastName(resultSet.getString(Fields.USER_LAST_NAME))
                .setPhone(resultSet.getString(Fields.USER_PHONE))
                .setRole(getById(resultSet.getInt(Fields.USER_ROLE_ID)))
                .build();
    }

    private User.Role getById(int id) {
        for(User.Role r : User.Role.values())
            if(r.getId() == (id)) return r;

        return null;
    }


}
