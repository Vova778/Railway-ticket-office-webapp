package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.Date;
import java.util.List;

public interface RouteService {
    void insert (Route route) throws ServiceException, FatalApplicationException;

    void delete (int routeId) throws ServiceException;

    boolean update(Route route) throws ServiceException;

    Route findById(int routeId) throws ServiceException;

    List<Route> findAll () throws ServiceException;

    List<Route> findRoutesByScheduleId (int scheduleId) throws ServiceException;

    List<Route> findRoutesByTicketId(int ticketId) throws ServiceException;

    List<Route> findAll(int offset) throws  ServiceException;

    List<Route> findRoutesBetweenStations(Date date,
                                          Station endStation,
                                          Station startStation) throws ServiceException;


    int countRecords() throws ServiceException;

}
