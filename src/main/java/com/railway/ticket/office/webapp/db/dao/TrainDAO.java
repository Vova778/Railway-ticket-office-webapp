package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.Connection;
import java.util.List;

public interface TrainDAO {
    Connection getConnection();

    int insert(Train train) throws DAOException;

    void delete(int trainId) throws DAOException;

    boolean update(Train train) throws DAOException;

    Train findByNumber(int trainId) throws DAOException;

    List<Train> findTrainBetweenStations(Station startStation,
                                         Station endStation) throws DAOException;

    List<Train> findAll(int offset) throws DAOException;

    int countRecords() throws DAOException;

}
