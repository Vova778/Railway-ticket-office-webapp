package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.StationDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.StationService;
import com.railway.ticket.office.webapp.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class StationServiceImpl implements StationService {
    private static final Logger LOGGER = LogManager.getLogger(StationServiceImpl.class);
    private static final String NULL_STATION_DAO_EXC =
            "[StationService] Can't create StationService with null input ScheduleDAO";
    private static final String NULL_STATION_INPUT_EXC =
            "[StationService] Can't operate null input!";
    private static final String EXISTED_STATION_EXC =
            "[StationService] Station with given ID: [{}] is already registered!";
    private final StationDAO stationDAO;

    public StationServiceImpl(StationDAO stationDAO){
        if (stationDAO == null) {
            LOGGER.error(NULL_STATION_DAO_EXC);
            throw new IllegalArgumentException(NULL_STATION_DAO_EXC);
        }
        this.stationDAO = stationDAO;
    }

    @Override
    public void insert(Station station) throws ServiceException, FatalApplicationException {
        if (station == null) {
            LOGGER.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            stationDAO.insertStation(station);
        } catch (SQLException e) {
            LOGGER.error("[StationService] SQLException while saving Station (id: {}). Exc: {}"
                    , station.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void checkAndSave(Station station) throws ServiceException, SQLException {
        stationDAO.getConnection().setAutoCommit(false);
        try {
            if (stationDAO.findStationById(station.getId()).getId() != 0) {
                DBUtils.rollback(stationDAO.getConnection());
                LOGGER.error(EXISTED_STATION_EXC
                        , station.getId());
                throw new ServiceException(EXISTED_STATION_EXC);
            }
            stationDAO.insertStation(station);
            stationDAO.getConnection().commit();
            stationDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[StationService] Schedule saved. (id: {})", station.getId());
        } catch (DAOException e) {
            stationDAO.getConnection().rollback();
            LOGGER.error("[StationService] Connection rolled back while saving Station. (id: {}). Exc: {}"
                    , station.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int stationId) throws ServiceException {
        if (stationId < 1) {
            LOGGER.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            stationDAO.deleteStation(stationId);
        } catch (DAOException e) {
            LOGGER.error("[StationService] An exception occurs while deleting Station. (id: {}). Exc: {}"
                    , stationId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(int stationId, Station station) throws ServiceException {
        if (stationId < 1 || station == null) {
            LOGGER.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            stationDAO.updateStation(stationId, station);
        } catch (DAOException e) {
            LOGGER.error("[StationService] An exception occurs while updating Schedule. (id: {}). Exc: {}"
                    , stationId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Station findStationById(int stationId) throws ServiceException {
        if (stationId < 1) {
            LOGGER.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            return stationDAO.findStationById(stationId);
        } catch (DAOException e) {
            LOGGER.error("[StationService] An exception occurs while receiving Station. (id: {}). Exc: {}"
                    , stationId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Station> findAll(int offset) throws ServiceException {
        try {
            return stationDAO.findAllStations(offset);
        } catch (DAOException e) {
            LOGGER.error("[StationService] An exception occurs while receiving Stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Station findStationByName(String stationName) throws ServiceException {
        if (stationName == null || stationName.equals("")) {
            LOGGER.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            return stationDAO.findStationByName(stationName);
        } catch (DAOException e) {
            LOGGER.error("[StationService] An exception occurs while receiving Station by name. (name: {}). Exc: {}"
                    , stationName, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int countRecords() throws ServiceException {
        try {
            return stationDAO.countRecords();
        } catch (DAOException e) {
            LOGGER.error("[StationService] An exception occurs while counting stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
