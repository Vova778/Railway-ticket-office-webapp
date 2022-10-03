package com.railway.ticket.office.webapp.db.dao.factory.impl;

import com.railway.ticket.office.webapp.db.dao.*;
import com.railway.ticket.office.webapp.db.dao.connections.ConnectionPoolHolder;
import com.railway.ticket.office.webapp.db.dao.factory.DAOFactory;
import com.railway.ticket.office.webapp.db.dao.impl.*;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DAOFactoryImpl extends DAOFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDAO getUserDAO() throws SQLException {
        return new UserDAOImpl(dataSource.getConnection());
    }

    @Override
    public StationDAO getStationDAO() throws SQLException {
        return new StationDAOImpl(dataSource.getConnection());
    }

    @Override
    public ScheduleDAO getScheduleDAO() throws SQLException {
        return new ScheduleDAOImpl(dataSource.getConnection());
    }

    @Override
    public TrainDAO getTrainDAO() throws SQLException {
        return new TrainDAOImpl(dataSource.getConnection());
    }

    @Override
    public TicketDAO getTicketDAO() throws SQLException {
        return new TicketDAOImpl(dataSource.getConnection());
    }

    @Override
    public RouteDAO getRouteDAO() throws SQLException {
        return new RouteDAOImpl(dataSource.getConnection());
    }
}
