package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;

import java.sql.Connection;
import java.util.List;

public interface StationDAO {
    Connection getConnection();

    int insert(Station station) throws DAOException;

    void delete(int stationId) throws DAOException;

    boolean update(Station station) throws DAOException;

    Station findById(int stationId) throws DAOException;

    Station findByName(String stationName) throws DAOException;

    List<Station> findAll(int offset) throws DAOException;

    List<Station> findAll() throws DAOException;

    int countRecords() throws DAOException;
}
