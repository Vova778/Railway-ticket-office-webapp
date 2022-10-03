package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RouteMapper implements ObjectMapper<Route> {

    @Override
    public Route extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String,Route> routeMap = new HashMap<>();

        Route route = Route.newBuilder()
                .setId(resultSet.getInt(Fields.ROUTE_ID))
                .setStoppageNumber(resultSet.getInt(Fields.ROUTE_STOPPAGE_NUMBER))
                .setStartingStationId(resultSet.getInt(Fields.ROUTE_STARTING_STATION_ID))
                .setFinalStationId(resultSet.getInt(Fields.ROUTE_FINAL_STATION_ID))
                .setDepartureTime(resultSet.getTime(Fields.ROUTE_DEPARTURE_TIME))
                .setArrivalTime(resultSet.getTime(Fields.ROUTE_ARRIVAL_TIME))
                .setAvailableSeats(resultSet.getInt(Fields.ROUTE_AVAILABLE_SEATS))
                .setDay(resultSet.getInt(Fields.ROUTE_DAY))
                .setScheduleId(resultSet.getInt(Fields.ROUTE_SCHEDULE_ID))
                .setTrainId(resultSet.getInt(Fields.ROUTE_TRAIN_ID))
                .setPrice(resultSet.getDouble(Fields.ROUTE_PRICE))
                .build();

        return route;
    }

    @Override
    public Route toUnique(Map<String, Route> cache, Route route) {
        return null;
    }
}
