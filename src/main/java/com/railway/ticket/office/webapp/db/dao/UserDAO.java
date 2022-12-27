package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {
    Connection getConnection();

    int insert(User user) throws DAOException;

    void delete(int userId) throws DAOException;

    boolean update(User user) throws DAOException;

    User findById(int userId) throws DAOException;

    User findByLogin(String login) throws DAOException;

    List<User> findAll(int offset) throws DAOException;
    int countRecords() throws DAOException;
}
