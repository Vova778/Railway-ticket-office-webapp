package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.UserDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.UserService;
import com.railway.ticket.office.webapp.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private static final String NULL_USER_DAO_EXC =
            "[UserService] Can't create UserService with null input UserDAO";
    private static final String NULL_USER_INPUT_EXC =
            "[UserService] Can't operate null input!";
    private static final String REGISTERED_EMAIL_EXC =
            "[UserService] User with given login: [{}] is already registered!";
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        if (userDAO == null) {
            LOGGER.error(NULL_USER_DAO_EXC);
            throw new IllegalArgumentException(NULL_USER_DAO_EXC);
        }
        this.userDAO = userDAO;
    }

    @Override
    public void insert(User user) throws ServiceException, FatalApplicationException {
        if (user == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
             checkAndSave(user);
        } catch (SQLException e) {
            LOGGER.error("[UserService] SQLException while saving User (email: {}). Exc: {}"
                    , user.getLogin(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(int userId, User user) throws ServiceException {
        if (userId < 1 || user == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            userDAO.updateUser(userId, user);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while updating User. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int userId) throws ServiceException {
        if (userId < 1) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            userDAO.deleteUser(userId);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while deleting User. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void checkAndSave(User user) throws ServiceException, SQLException {
        userDAO.getConnection().setAutoCommit(false);
        try {
            if (userDAO.findUserByLogin(user.getLogin()).getId() != 0) {
                DBUtils.rollback(userDAO.getConnection());
                LOGGER.error(REGISTERED_EMAIL_EXC
                        , user.getLogin());
                throw new ServiceException(REGISTERED_EMAIL_EXC);
            }
            userDAO.insertUser(user);
            userDAO.getConnection().commit();
            userDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[UserService] User saved. (email: {})", user.getLogin());
        } catch (DAOException e) {
            userDAO.getConnection().rollback();
            LOGGER.error("[UserService] Connection rolled back while saving User. (email: {}). Exc: {}"
                    , user.getLogin(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.findAllUsers();
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving Users. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User findById(int id) throws ServiceException {
        if (id < 1) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.findUserById(id);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving User. (id: {}). Exc: {}"
                    , id, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        if (login == null) {
            LOGGER.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.findUserByLogin(login);
        } catch (DAOException e) {
            LOGGER.error("[UserService] An exception occurs while receiving User. (email: {}). Exc: {}"
                    , login, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
