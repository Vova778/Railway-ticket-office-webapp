package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface StationDAO {
    Connection getConnection();

    int insertStation(Station station) throws DAOException;

    void deleteStation(int stationId) throws DAOException;

    boolean updateStation(int stationId, Station station) throws DAOException;

    Optional<Station> findStationById(int stationId) throws DAOException;

    Optional<Station> findStationByName(String stationName) throws DAOException;

    List<Station> findAllStations(int offset) throws DAOException;

    List<Station> findAllStations() throws DAOException;

    int countRecords() throws DAOException;
}
