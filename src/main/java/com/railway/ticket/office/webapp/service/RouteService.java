package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Ticket;

import java.util.List;

public interface RouteService {
    void insert (Route route) throws ServiceException, FatalApplicationException;

    void delete (int routeId) throws ServiceException;

    void update (int routeId, Route route) throws ServiceException;

    boolean updateTicketRoutes (Ticket ticket) throws ServiceException;

    Route findRouteById (int routeId) throws ServiceException;

    List<Route> findAll () throws ServiceException;

    List<Route> findRoutesByScheduleId (int scheduleId) throws ServiceException;

    List<Route> findRoutesByTicketId(int ticketId) throws ServiceException;


    List<Route> findAll(int offset) throws  ServiceException;


    List<Route> findRoutesBetweenStations(Schedule schedule,
                                          Station startStation,
                                          Station endStation) throws ServiceException;


    int countRecords() throws ServiceException;

}
