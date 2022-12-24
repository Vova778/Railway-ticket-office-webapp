package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface RouteDAO {
    Connection getConnection();

    int insertRoute(Route route) throws DAOException;

    void deleteRoute(int routeId) throws DAOException;

    boolean updateRoute(int routeId, Route route) throws DAOException;

    Optional<Route> findRouteById(int routeId) throws DAOException;

    List<Route> findRoutesByScheduleId(int scheduleId) throws DAOException;

    List<Route> findRoutesByTicketId(int ticketId) throws DAOException;

    List<Route> findAllRoutes() throws DAOException;

    List<Route> findAllRoutes(int offset) throws DAOException;

    List<Route> findRoutesBetweenStations(Date date,
                                          Station endStation,
                                          Station startStation) throws DAOException;

    int countRecords() throws DAOException;
}
