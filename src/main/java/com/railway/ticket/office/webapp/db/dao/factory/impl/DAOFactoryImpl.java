package com.railway.ticket.office.webapp.db.dao.factory.impl;

import com.railway.ticket.office.webapp.db.dao.*;
import com.railway.ticket.office.webapp.db.dao.connections.ConnectionPoolHolder;
import com.railway.ticket.office.webapp.db.dao.factory.DAOFactory;
import com.railway.ticket.office.webapp.db.dao.impl.*;
import com.railway.ticket.office.webapp.exceptions.DAOException;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DAOFactoryImpl extends DAOFactory {
    public static final String MYSQL_PROPS_PATH = "/mysql/db.properties";
    private final DataSource dataSource =
            ConnectionPoolHolder.getDataSource(MYSQL_PROPS_PATH);

    public DAOFactoryImpl() throws DAOException {
    }

    /* DAO Factory methods */

    /**
     * Creates User DAO
     * @return User DAO
     */

    @Override
    public UserDAO getUserDAO() throws SQLException {
        return new UserDAOImpl(dataSource.getConnection());
    }


    /**
     * Creates Station DAO
     * @return Station DAO
     */

    @Override
    public StationDAO getStationDAO() throws SQLException {
        return new StationDAOImpl(dataSource.getConnection());
    }

    /**
     * Creates Schedule DAO
     * @return Schedule DAO
     */
    @Override
    public ScheduleDAO getScheduleDAO() throws SQLException {
        return new ScheduleDAOImpl(dataSource.getConnection());
    }

    /**
     * Creates Train DAO
     * @return Train DAO
     */
    @Override
    public TrainDAO getTrainDAO() throws SQLException {
        return new TrainDAOImpl(dataSource.getConnection());
    }

    /**
     * Creates Ticket DAO
     * @return Ticket DAO
     */
    @Override
    public TicketDAO getTicketDAO() throws SQLException {
        return new TicketDAOImpl(dataSource.getConnection());
    }

    /**
     * Creates Route DAO
     * @return Route DAO
     */
    @Override
    public RouteDAO getRouteDAO() throws SQLException {
        return new RouteDAOImpl(dataSource.getConnection());
    }
}
