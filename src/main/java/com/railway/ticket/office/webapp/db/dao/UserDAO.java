package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {
    Connection getConnection();

    void insertUser(User user) throws DAOException;

    void deleteUser(int userId) throws DAOException;

    void updateUser(int userId, User user) throws DAOException;

    User findUserById(int userId) throws DAOException;

    User findUserByLogin(String login) throws DAOException;

    List<User> findAllUsers() throws DAOException;
}
