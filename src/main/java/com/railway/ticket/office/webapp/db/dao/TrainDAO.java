package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface TrainDAO {
    Connection getConnection();

    int insertTrain(Train train) throws DAOException;

    void deleteTrain(int trainId) throws DAOException;

    boolean updateTrain(int trainId, Train train) throws DAOException;

    Optional<Train> findTrainByNumber(int trainId) throws DAOException;

    List<Train> findTrainBetweenStations(Station startStation,
                                         Station endStation) throws DAOException;

    List<Train> findAllTrains(int offset) throws DAOException;
    int countRecords() throws DAOException;

}
