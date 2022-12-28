package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.RouteDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * This class implements business logic for {@link Route}
 */
public class RouteServiceImpl implements RouteService {
    private static final Logger log = LogManager.getLogger(RouteServiceImpl.class);
    private static final String NULL_ROUTE_DAO_EXC =
            "[RouteService] Can't create RouteService with null input RouteDAO";
    private static final String NULL_ROUTE_INPUT_EXC =
            "[RouteService] Can't operate null or < 1 input!";

    private final RouteDAO routeDAO;

    public RouteServiceImpl(RouteDAO routeDAO){
        if (routeDAO == null) {
            log.error(NULL_ROUTE_DAO_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_DAO_EXC);
        }
        this.routeDAO = routeDAO;
    }

    @Override
    public void insert(Route route) throws ServiceException {
        if (route == null) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            route.setId( routeDAO.insert(route));
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
            routeDAO.delete(routeId);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while deleting Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Route route) throws ServiceException {
        if ( route == null || route.getId() < 1 ) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.update(route);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while updating Route. (id: {}). Exc: {}"
                    , route.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Route findById(int routeId) throws ServiceException {
        if (routeId < 1) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.findById(routeId);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Route> findAll() throws ServiceException {
        try {
            return routeDAO.findAll();
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findAll(int offset) throws ServiceException {
        if (offset < 0) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.findAll(offset);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<Route> findRoutesByScheduleId(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
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
        if (ticketId < 1) {
            log.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.findRoutesByTicketId(ticketId);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes by ticket id. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findRoutesBetweenStations(Date date,
                                                 Station endStation,
                                                 Station startStation) throws ServiceException {
        try {
            return routeDAO.findRoutesBetweenStations(date, endStation, startStation);
        } catch (DAOException e) {
            log.error("[RouteService] An exception occurs while receiving Routes between stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * @return all {@link Route} records in database
     */
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
