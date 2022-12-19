package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.RouteDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private static final Logger log = LogManager.getLogger(RouteServiceImpl.class);
    private static final String NULL_ROUTE_DAO_EXC =
            "[RouteService] Can't create RouteService with null input RouteDAO";
    private static final String NULL_ROUTE_INPUT_EXC =
            "[RouteService] Can't operate null input!";
    private static final String EXISTED_ROUTE_EXC =
            "[RouteService] Route with given ID: [{}] is already registered!";
    private final RouteDAO routeDAO;

    public RouteServiceImpl(RouteDAO routeDAO){
        if (routeDAO == null) {
            log.error(NULL_ROUTE_DAO_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_DAO_EXC);
        }
        this.routeDAO = routeDAO;
    }

    @Override
    public void insert(Route route) throws ServiceException, FatalApplicationException {
        if (route == null) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            route.setId( routeDAO.insertRoute(route));
            log.info("[RouteService] Route saved. (id: {})", route.getId());
        } catch (SQLException e) {
            log.error("[RouteService] SQLException while saving Route (id: {}). Exc: {}"
                    , route.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public void delete(int routeId) throws ServiceException {
        if (routeId < 1) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            routeDAO.deleteRoute(routeId);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while deleting Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(int routeId, Route route) throws ServiceException {
        if (routeId < 1 || route == null) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.updateRoute(routeId, route);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while updating Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public boolean updateTicketRoutes(Ticket ticket) throws ServiceException {
        if ( ticket == null) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            routeDAO.updateTicketRoutes(ticket);
            return true;
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while updating Route by Ticket Id. (Ticket Id: {}). Exc: {}"
                    , ticket.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Route findRouteById(int routeId) throws ServiceException {
        if (routeId < 1) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.findRouteById(routeId).get();
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Route> findAll() throws ServiceException {
        try {
            return routeDAO.findAllRoutes();
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findAll(int offset) throws ServiceException {
        try {
            return routeDAO.findAllRoutes(offset);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<Route> findRoutesByScheduleId(int scheduleId) throws ServiceException {
        try {
            return routeDAO.findRoutesByScheduleId(scheduleId);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes by schedule id. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findRoutesByTicketId(int ticketId) throws ServiceException {
        try {
            return routeDAO.findRoutesByTicketId(ticketId);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes by ticket id. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findRoutesBetweenStations( Station startStation, Station endStation) throws ServiceException {
        try {
            return routeDAO.findRoutesBetweenStations(
                    startStation,
                    endStation);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes between stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int countRecords() throws ServiceException {
        try {
            return routeDAO.countRecords();
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while counting routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
