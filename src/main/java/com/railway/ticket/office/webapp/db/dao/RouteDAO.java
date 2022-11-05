package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Ticket;

import java.sql.Connection;
import java.util.List;

public interface RouteDAO {
    Connection getConnection();

    void insertRoute(Route route) throws DAOException;

    void deleteRoute(int routeId) throws DAOException;

    void updateRoute(int routeId, Route route) throws DAOException;

    boolean updateTicketRoutes (Ticket ticket) throws DAOException;

    Route findRouteById(int routeId) throws DAOException;

    List<Route> findRoutesByScheduleId(int scheduleId) throws DAOException;

    List<Route> findRoutesByTicketId(int ticketId) throws DAOException;

    List<Route> findAllRoutes() throws DAOException;

    List<Route> findAllRoutes(int offset) throws DAOException;

    List<Route> findRoutesBetweenStations(Schedule schedule,
                                          Station startStation,
                                          Station endStation) throws DAOException;
    int countRecords() throws DAOException;
}
