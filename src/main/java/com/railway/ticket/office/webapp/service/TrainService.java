package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Train;

import java.util.List;

public interface TrainService {
    void insert (Train train) throws ServiceException, FatalApplicationException;

    void delete (int trainId) throws ServiceException;

    boolean update (int trainId, Train train) throws ServiceException;

    Train findTrainById (int trainId) throws ServiceException;

    List<Train> findAll (int offset) throws ServiceException;

    int countRecords() throws ServiceException;
}
