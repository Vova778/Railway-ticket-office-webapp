package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface RouteDAO {
    Connection getConnection();

    int insert(Route route) throws DAOException;

    void delete(int routeId) throws DAOException;

    boolean update(Route route) throws DAOException;

    Route findById(int routeId) throws DAOException;

    List<Route> findRoutesByScheduleId(int scheduleId) throws DAOException;

    List<Route> findRoutesByTicketId(int ticketId) throws DAOException;

    List<Route> findAll() throws DAOException;

    List<Route> findAll(int offset) throws DAOException;

    List<Route> findRoutesBetweenStations(Date date,
                                          Station endStation,
                                          Station startStation) throws DAOException;

    int countRecords() throws DAOException;
}
