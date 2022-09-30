package com.railway.ticket.officewebapp.db.dao;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.model.Route;

import java.util.List;

public interface RouteDAO {
    void insertRoute(Route route) throws DBException;
    void deleteRoute(int routeId) throws DBException;
    void updateRoute(int routeId, Route route) throws DBException;
    Route getRouteById(int routeId) throws DBException;
    List<Route> findAllRoute() throws DBException;
}
