package com.railway.ticket.office.webapp.db.dao.impl;

import com.railway.ticket.office.webapp.db.Constants;
import com.railway.ticket.office.webapp.db.dao.UserDAO;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.UserMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final Connection con;
    private static final Logger log = LogManager.getLogger(UserDAOImpl.class);
    private final UserMapper userMapper = new UserMapper();

    public UserDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Connection getConnection() {
        return con;
    }

    @Override
    public int insertUser(User user) throws DAOException {

        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.USERS_INSERT_USER,
                             Statement.RETURN_GENERATED_KEYS)) {

            setUserParameters(user, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int key = 0;
            if (resultSet.next()) {

                key = resultSet.getInt(1);
                log.info("User : {} was inserted successfully", user);
            }
            return key;
        } catch (SQLException e) {
            log.error("User : [{}] wasn`t inserted. An exception occurs : {}",
                    user, e.getMessage());
            throw new DAOException("[UserDAO] exception while creating User" + e.getMessage(), e);
        }
    }


    @Override
    public void deleteUser(int userId) throws DAOException {
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.USERS_DELETE_USER)) {

            preparedStatement.setInt(1, userId);

            int removedRow = preparedStatement.executeUpdate();

            if (removedRow > 0) {
                log.info("User with ID : {} was removed successfully", userId);
            }

        } catch (SQLException e) {
            log.error("User with ID : [{}] was not removed. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while removing User" + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateUser(int userId, User user) throws DAOException {


        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.USERS_UPDATE_USER)) {

            setUserParameters(user, preparedStatement);
            preparedStatement.setInt(7, userId);

            int updatedRow = preparedStatement.executeUpdate();
            if (updatedRow > 0) {
                log.info("User with ID : {} was updated successfully", userId);
                return true;
            } else {
                log.info("User with ID : [{}] was not found for update", userId);
                return false;
            }

        } catch (SQLException e) {
            log.error("User with ID : [{}] was not updated. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while updating User" + e.getMessage(), e);
        }
    }

    private void setUserParameters(User user, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, user.getLogin());
        preparedStatement.setString(k++, user.getPassword());
        preparedStatement.setString(k++, user.getFirstName());
        preparedStatement.setString(k++, user.getLastName());
        preparedStatement.setString(k++, user.getPhone());
        int roleId = user.getRole().getId();
        preparedStatement.setInt(k, roleId);
    }


    @Override
    public Optional<User> findUserById(int userId) throws DAOException {
        Optional<User> user = Optional.empty();

        try (PreparedStatement preparedStatement
                     = con.prepareStatement(Constants.USERS_GET_USER_BY_ID)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.ofNullable(userMapper
                            .extractFromResultSet(resultSet));
                }
                user.ifPresent(u -> log.info("User was received : [{}], [{}]",
                        u.getId(), u.getLogin()));
            }

        } catch (SQLException e) {
            log.error("User with ID : [{}] was not found. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[UserDAO] exception while loading User by ID" + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DAOException {
        Optional<User> user = Optional.empty();

        try (PreparedStatement preparedStatement
                     = con.prepareStatement(Constants.USERS_GET_USER_BY_LOGIN)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = Optional.ofNullable(userMapper
                            .extractFromResultSet(resultSet));
                }
                user.ifPresent(u -> log.info("User was received : [{}], [{}]",
                        u.getId(), u.getLogin()));
            }
            return user;
        } catch (SQLException e) {
            log.error("User with login : [{}] was not found. An exception occurs : {}",
                    login, e.getMessage());
            throw new DAOException("[UserDAO] exception while loading User by login" + e.getMessage(), e);
        }

    }

    @Override
    public List<User> findAllUsers(int offset) throws DAOException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement
                     = con.prepareStatement(Constants.USERS_GET_ALL_USERS)) {

            preparedStatement.setInt(1, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(userMapper
                            .extractFromResultSet(resultSet));
                }
            }
            return users;
        } catch (SQLException e) {
            log.error("Users were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[UserDAO] exception while reading all users" + e.getMessage(), e);
        }

    }

    @Override
    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.USERS_GET_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            log.error("[UserDAO] Failed to count users!" +
                            " An exception occurs :[{}]",
                    e.getMessage());
        }
        return recordsCount;
    }

}
