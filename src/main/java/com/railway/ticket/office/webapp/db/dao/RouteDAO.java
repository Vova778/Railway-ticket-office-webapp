package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;

import java.sql.Connection;
import java.util.List;

public interface RouteDAO {
    Connection getConnection();

    void insertRoute(Route route) throws DAOException;

    void deleteRoute(int routeId) throws DAOException;

    void updateRoute(int routeId, Route route) throws DAOException;

    Route findRouteById(int routeId) throws DAOException;

    List<Route> findRoutesByScheduleId(int scheduleId) throws DAOException;

    List<Route> findAllRoutes() throws DAOException;
    List<Route> findAllRoutesWithOffset(int offset) throws DAOException;
    int countRecords() throws DAOException;
}
