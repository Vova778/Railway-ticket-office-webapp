package com.railway.ticket.officewebapp.db.dao;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.model.Station;

import java.util.List;

public interface StationDAO {
    void insertStation(Station station) throws DBException;

    void deleteStation(int stationId) throws DBException;

    void updateStation(int stationId, Station station) throws DBException;

    Station getStationById(int stationId) throws DBException;

    Station getStationByName(String stationName) throws DBException;

    List<Station> findAllStations() throws DBException;
}
