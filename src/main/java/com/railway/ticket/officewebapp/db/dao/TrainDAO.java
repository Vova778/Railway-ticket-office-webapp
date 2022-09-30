package com.railway.ticket.officewebapp.db.dao;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.model.Station;
import com.railway.ticket.officewebapp.model.Train;

import java.util.List;

public interface TrainDAO {
    void insertTrain(Train train) throws DBException;

    void deleteTrain(int trainId) throws DBException;

    void updateTrain(int trainId, Train train) throws DBException;

    Train getTrainById(int trainId) throws DBException;

    List<Train> getTrainBetweenStations(Station startStation, Station endStation) throws DBException;

    List<Train> findAllTrains() throws DBException;
}
