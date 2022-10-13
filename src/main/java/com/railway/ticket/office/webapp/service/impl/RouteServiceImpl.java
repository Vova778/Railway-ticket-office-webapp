package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.RouteDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.service.RouteService;
import com.railway.ticket.office.webapp.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private static final Logger LOGGER = LogManager.getLogger(RouteServiceImpl.class);
    private static final String NULL_ROUTE_DAO_EXC =
            "[RouteService] Can't create RouteService with null input RouteDAO";
    private static final String NULL_ROUTE_INPUT_EXC =
            "[RouteService] Can't operate null input!";
    private static final String EXISTED_ROUTE_EXC =
            "[RouteService] Route with given ID: [{}] is already registered!";
    private final RouteDAO routeDAO;

    public RouteServiceImpl(RouteDAO routeDAO){
        if (routeDAO == null) {
            LOGGER.error(NULL_ROUTE_DAO_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_DAO_EXC);
        }
        this.routeDAO = routeDAO;
    }

    @Override
    public void insert(Route route) throws ServiceException, FatalApplicationException {
        if (route == null) {
            LOGGER.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            routeDAO.insertRoute(route);
        } catch (SQLException e) {
            LOGGER.error("[RouteService] SQLException while saving Route (id: {}). Exc: {}"
                    , route.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void checkAndSave(Route route) throws ServiceException, SQLException {
        routeDAO.getConnection().setAutoCommit(false);
        try {
            if (routeDAO.findRouteById(route.getId()).getId() != 0) {
                DBUtils.rollback(routeDAO.getConnection());
                LOGGER.error(EXISTED_ROUTE_EXC
                        , route.getId());
                throw new ServiceException(EXISTED_ROUTE_EXC);
            }
            routeDAO.insertRoute(route);
            routeDAO.getConnection().commit();
            routeDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[RouteService] Route saved. (id: {})", route.getId());
        } catch (DAOException e) {
            routeDAO.getConnection().rollback();
            LOGGER.error("[RouteService] Connection rolled back while saving Route. (id: {}). Exc: {}"
                    , route.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int routeId) throws ServiceException {
        if (routeId < 1) {
            LOGGER.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            routeDAO.deleteRoute(routeId);
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while deleting Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(int routeId, Route route) throws ServiceException {
        if (routeId < 1 || route == null) {
            LOGGER.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            routeDAO.updateRoute(routeId, route);
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while updating Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Route findRouteById(int routeId) throws ServiceException {
        if (routeId < 1) {
            LOGGER.error(NULL_ROUTE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_ROUTE_INPUT_EXC);
        }
        try {
            return routeDAO.findRouteById(routeId);
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while receiving Route. (id: {}). Exc: {}"
                    , routeId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Route> findAll() throws ServiceException {
        try {
            return routeDAO.findAllRoutes();
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while receiving Routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findAllRoutesWithOffset(int offset) throws ServiceException {
        try {
            return routeDAO.findAllRoutesWithOffset(offset);
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while receiving Routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<Route> findRoutesByScheduleId(int scheduleId) throws ServiceException {
        try {
            return routeDAO.findRoutesByScheduleId(scheduleId);
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while receiving Routes by schedule id. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int countRecords() throws ServiceException {
        try {
            return routeDAO.countRecords();
        } catch (DAOException e) {
            LOGGER.error("[RouteService] An exception occurs while counting routes. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
