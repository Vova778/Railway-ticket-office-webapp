package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Connection getConnection();

    int insertUser(User user) throws DAOException;

    void deleteUser(int userId) throws DAOException;

    boolean updateUser(int userId, User user) throws DAOException;

    Optional<User> findUserById(int userId) throws DAOException;

    Optional<User> findUserByLogin(String login) throws DAOException;

    List<User> findAllUsers(int offset) throws DAOException;
    int countRecords() throws DAOException;
}
