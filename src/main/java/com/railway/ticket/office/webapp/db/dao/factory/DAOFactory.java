package com.railway.ticket.office.webapp.db.dao.factory;


import com.railway.ticket.office.webapp.db.dao.*;
import com.railway.ticket.office.webapp.db.dao.factory.impl.DAOFactoryImpl;
import com.railway.ticket.office.webapp.exceptions.DAOException;

import java.sql.SQLException;

public abstract class DAOFactory {

    private static DAOFactory daoFactory;

    public static DAOFactory getInstance() throws DAOException {
        if (daoFactory == null) {
            synchronized (DAOFactory.class) {
                daoFactory = new DAOFactoryImpl();
            }
        }
        return daoFactory;
    }

    public abstract UserDAO getUserDAO() throws SQLException;
    public abstract StationDAO getStationDAO() throws SQLException;
    public abstract ScheduleDAO getScheduleDAO() throws SQLException;
    public abstract TrainDAO getTrainDAO() throws SQLException;
    public abstract TicketDAO getTicketDAO() throws SQLException;
    public abstract RouteDAO getRouteDAO() throws SQLException;

}
