package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.Connection;
import java.util.List;

public interface StationDAO {
    Connection getConnection();

    void insertStation(Station station) throws DAOException;

    void deleteStation(int stationId) throws DAOException;

    void updateStation(int stationId, Station station) throws DAOException;

    Station findStationById(int stationId) throws DAOException;

    Station findStationByName(String stationName) throws DAOException;

    List<Station> findAllStations(int offset) throws DAOException;

    int countRecords() throws DAOException;
}
