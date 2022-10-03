package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.Connection;
import java.util.List;

public interface TrainDAO {
    Connection getConnection();

    void insertTrain(Train train) throws DAOException;

    void deleteTrain(int trainId) throws DAOException;

    void updateTrain(int trainId, Train train) throws DAOException;

    Train findTrainByNumber(int trainId) throws DAOException;

    List<Train> findTrainBetweenStations(Station startStation, Station endStation) throws DAOException;

    List<Train> findAllTrains() throws DAOException;
}
