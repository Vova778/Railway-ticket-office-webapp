package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.TrainDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.TrainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TrainServiceImpl implements TrainService {

    private static final Logger LOGGER = LogManager.getLogger(TrainServiceImpl.class);
    private static final String NULL_TRAIN_DAO_EXC =
            "[TrainService] Can't create TrainService with null input TrainDAO";
    private static final String NULL_TRAIN_INPUT_EXC =
            "[TrainService] Can't operate null input!";
    private static final String EXISTED_TRAIN_EXC =
            "[TrainService] Train with given number: [{}] is already registered!";
    private final TrainDAO trainDAO;

    public TrainServiceImpl(TrainDAO trainDAO){
        if (trainDAO == null) {
            LOGGER.error(NULL_TRAIN_DAO_EXC);
            throw new IllegalArgumentException(NULL_TRAIN_DAO_EXC);
        }
        this.trainDAO = trainDAO;
    }

    @Override
    public void insert(Train train) throws ServiceException {
        if (train == null) {
            LOGGER.error(NULL_TRAIN_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TRAIN_INPUT_EXC);
        }
        try {
            train.setNumber(trainDAO.insert(train)); ;
        } catch (SQLException e) {
            LOGGER.error("[TrainService] SQLException while saving Train (number: {}). Exc: {}"
                    , train.getNumber(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int trainId) throws ServiceException {
        if (trainId < 1) {
            LOGGER.error(NULL_TRAIN_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TRAIN_INPUT_EXC);
        }
        try {
            trainDAO.delete(trainId);
        } catch (DAOException e) {
            LOGGER.error("[TrainService] An exception occurs while deleting Train. (number: {}). Exc: {}"
                    , trainId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Train train) throws ServiceException {
        if ( train == null || train.getNumber() < 1) {
            LOGGER.error(NULL_TRAIN_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TRAIN_INPUT_EXC);
        }
        try {
            return trainDAO.update(train);
        } catch (DAOException e) {
            LOGGER.error("[TrainService] An exception occurs while updating Train. (number: {}). Exc: {}"
                    , train.getNumber(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Train findById(int trainId) throws ServiceException {
        if (trainId < 1) {
            LOGGER.error(NULL_TRAIN_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TRAIN_INPUT_EXC);
        }
        try {
            return trainDAO.findByNumber(trainId);
        } catch (DAOException e) {
            LOGGER.error("[TrainService] An exception occurs while receiving train. (number: {}). Exc: {}"
                    , trainId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Train> findAll(int offset) throws ServiceException {
        try {
            return trainDAO.findAll(offset);
        } catch (DAOException e) {
            LOGGER.error("[TrainService] An exception occurs while receiving Trains. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public int countRecords() throws ServiceException {
        try {
            return trainDAO.countRecords();
        } catch (DAOException e) {
            LOGGER.error("[TrainService] An exception occurs while counting trains. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
