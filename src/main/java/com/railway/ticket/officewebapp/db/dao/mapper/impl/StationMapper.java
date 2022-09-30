package com.railway.ticket.officewebapp.db.dao.mapper.impl;

import com.railway.ticket.officewebapp.db.Fields;
import com.railway.ticket.officewebapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.officewebapp.model.Station;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StationMapper implements ObjectMapper<Station> {
    
    @Override
    public Station extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Station> stationMap = new HashMap<>();
        Station station = new Station();

        station.setId(resultSet.getInt(Fields.STATION_ID));
        station.setName(resultSet.getString(Fields.STATION_NAME));

        return station;
    }

    @Override
    public Station toUnique(Map<String, Station> cache, Station station) {
        return null;
    }
}
