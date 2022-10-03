package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.User;

import java.util.List;

public interface UserService {
    void insert(User user) throws ServiceException, FatalApplicationException;

    void update(int userId, User user) throws ServiceException;

    void delete(int userId) throws ServiceException;

    List<User> findAll() throws ServiceException;


    User findById(int id) throws ServiceException;

    User findByLogin(String login) throws ServiceException;




}
