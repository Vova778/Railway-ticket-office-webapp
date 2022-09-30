package com.railway.ticket.officewebapp.db.dao;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.model.User;

import java.util.List;

public interface UserDAO {
    void insertUser(User user) throws DBException;

    void deleteUser(int userId) throws DBException;

    void updateUser(int userId, User user) throws DBException;

    User getUserById(int userId) throws DBException;

    User getUserByLogin(String login) throws DBException;

    List<User> findAllUsers() throws DBException;
}
