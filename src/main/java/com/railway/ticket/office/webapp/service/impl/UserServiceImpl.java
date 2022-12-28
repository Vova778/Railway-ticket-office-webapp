package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.UserDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * This class implements business logic for {@link User}
 */
public class UserServiceImpl implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
    private static final String NULL_USER_DAO_EXC =
            "[UserService] Can't create UserService with null input UserDAO";
    private static final String NULL_USER_INPUT_EXC =
            "[UserService] Can't operate null input!";
    private static final String REGISTERED_LOGIN_EXC =
            "[UserService] User with given login: [{}] is already registered!";
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO){
        if (userDAO == null) {
            log.error(NULL_USER_DAO_EXC);
            throw new IllegalArgumentException(NULL_USER_DAO_EXC);
        }
        this.userDAO = userDAO;
    }

    @Override
    public void insert(User user) throws ServiceException {
        if (user == null) {
            log.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            user.setId( userDAO.insert(user));
            log.info("[UserService] User saved. (login: {})", user.getLogin());

        } catch (DAOException e) {
            log.error("[UserService] SQLException while saving User (login: {}). Exc: {}"
                    , user.getLogin(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(User user) throws ServiceException {
        if ( user == null || user.getId() < 1) {
            log.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
           return   userDAO.update(user);
        } catch (DAOException e) {
            log.error("[UserService] An exception occurs while updating User. (id: {}). Exc: {}"
                    , user.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int userId) throws ServiceException {
        if (userId < 1) {
            log.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            userDAO.delete(userId);
        } catch (DAOException e) {
            log.error("[UserService] An exception occurs while deleting User. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    public boolean isUserExists(User user) throws ServiceException {
        try {
            if (userDAO.findByLogin(user.getLogin()).getId()!=0) {
                log.info(REGISTERED_LOGIN_EXC
                        , user.getLogin());
                return true;
            }
            return false;
        } catch (DAOException e) {
            log.error("[UserService] Connection rolled back while saving User. (login: {}). Exc: {}"
                    , user.getLogin(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User findById(int id) throws ServiceException {
        if (id < 1) {
            log.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.findById(id);
        } catch (DAOException e) {
            log.error("[UserService] An exception occurs while receiving User. (id: {}). Exc: {}"
                    , id, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        if (login == null||login.equals("")) {
            log.error(NULL_USER_INPUT_EXC);
            throw new IllegalArgumentException(NULL_USER_INPUT_EXC);
        }
        try {
            return userDAO.findByLogin(login);
        } catch (DAOException e) {
            log.error("[UserService] An exception occurs while receiving User. (email: {}). Exc: {}"
                    , login, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<User> findAll(int offset) throws ServiceException {
        try {
            return userDAO.findAll(offset);
        } catch (DAOException e) {
            log.error("[UserService] An exception occurs while receiving Users. Exc: {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * @return all {@link User} records in database
     */
    @Override
    public int countRecords() throws ServiceException {
        try {
            return userDAO.countRecords();
        } catch (DAOException e) {
            log.error("[UserService] An exception occurs while counting users. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
