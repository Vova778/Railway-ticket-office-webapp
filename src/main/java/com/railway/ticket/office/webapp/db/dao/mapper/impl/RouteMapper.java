package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RouteMapper implements ObjectMapper<Route> {

    @Override
    public Route extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String,Route> routeMap = new HashMap<>();
        Train train = new Train(
                resultSet.getInt(Fields.ROUTE_TRAIN_ID),
                resultSet.getInt(Fields.ROUTE_TOTAL_SEATS)
        );

        Schedule schedule = new Schedule(
                resultSet.getInt(Fields.ROUTE_SCHEDULE_ID),
                resultSet.getDate(Fields.SCHEDULE_DATE)
        );

        Station startingStation = new Station(
                resultSet.getInt(Fields.ROUTE_STARTING_STATION_ID)
                ,resultSet.getString(Fields.ROUTE_STARTING_STATION_NAME));

        Station finalStation = new Station(
                resultSet.getInt(Fields.ROUTE_FINAL_STATION_ID)
                ,resultSet.getString(Fields.ROUTE_FINAL_STATION_NAME));

        Route route = Route.newBuilder()
                .setId(resultSet.getInt(Fields.ROUTE_ID))
                .setStoppageNumber(resultSet.getInt(Fields.ROUTE_STOPPAGE_NUMBER))
                .setStartingStation( startingStation)
                .setFinalStation(finalStation)
                .setDepartureTime(resultSet.getTime(Fields.ROUTE_DEPARTURE_TIME))
                .setArrivalTime(resultSet.getTime(Fields.ROUTE_ARRIVAL_TIME))
                .setAvailableSeats(resultSet.getInt(Fields.ROUTE_AVAILABLE_SEATS))
                .setDay(resultSet.getInt(Fields.ROUTE_DAY))
                .setSchedule(schedule)
                .setTrain(train)
                .setPrice(resultSet.getDouble(Fields.ROUTE_PRICE))
                .build();

        return route;
    }

    @Override
    public Route toUnique(Map<String, Route> cache, Route route) {
        return null;
    }
}
