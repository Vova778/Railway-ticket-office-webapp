package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StationMapper implements ObjectMapper<Station> {
    
    @Override
    public Station extractFromResultSet(ResultSet resultSet) throws SQLException {
        Station station = new Station();

        station.setId(resultSet.getInt(Fields.STATION_ID));
        station.setName(resultSet.getString(Fields.STATION_NAME));

        return station;
    }

}
