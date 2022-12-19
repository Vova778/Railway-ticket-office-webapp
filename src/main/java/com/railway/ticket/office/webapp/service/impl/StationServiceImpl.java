package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.StationDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StationServiceImpl implements StationService {
    private static final Logger log = LogManager.getLogger(StationServiceImpl.class);
    private static final String NULL_STATION_DAO_EXC =
            "[StationService] Can't create StationService with null input StationDAO";
    private static final String NULL_STATION_INPUT_EXC =
            "[StationService] Can't operate null input!";

    private final StationDAO stationDAO;

    public StationServiceImpl(StationDAO stationDAO) {
        if (stationDAO == null) {
            log.error(NULL_STATION_DAO_EXC);
            throw new IllegalArgumentException(NULL_STATION_DAO_EXC);
        }
        this.stationDAO = stationDAO;
    }

    @Override
    public void insert(Station station) throws ServiceException, FatalApplicationException {
        if (station == null) {
            log.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            if(stationDAO.findStationByName(station.getName()).isPresent()){
                log.error("[StationService] Station with name [{}] already exists", station.getName());
                throw new ServiceException("Station with name [{}] already exists");
            }
            station.setId(stationDAO.insertStation(station));
        } catch (SQLException e) {
            log.error("[StationService] SQLException while saving Station (id: {}). Exc: {}"
                    , station.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public void delete(int stationId) throws ServiceException {
        if (stationId < 1) {
            log.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            stationDAO.deleteStation(stationId);
        } catch (DAOException e) {
            log.error("[StationService] An exception occurs while deleting Station. (id: {}). Exc: {}"
                    , stationId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(int stationId, Station station) throws ServiceException {
        if (stationId < 1 || station == null) {
            log.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            return stationDAO.updateStation(stationId, station);
        } catch (DAOException e) {
            log.error("[StationService] An exception occurs while updating Schedule. (id: {}). Exc: {}"
                    , stationId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Station> findStationById(int stationId) throws ServiceException {
        if (stationId < 1) {
            log.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            return stationDAO.findStationById(stationId);
        } catch (DAOException e) {
            log.error("[StationService] An exception occurs while receiving Station. (id: {}). Exc: {}"
                    , stationId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Station> findAll(int offset) throws ServiceException {
        try {
            return stationDAO.findAllStations(offset);
        } catch (DAOException e) {
            log.error("[StationService] An exception occurs while receiving Stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Station> findAll() throws ServiceException {
        try {
            return stationDAO.findAllStations();
        } catch (DAOException e) {
            log.error(
                    "[StationService] An exception occurs while receiving Stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Optional<Station> findStationByName(String stationName) throws ServiceException {
        if (stationName == null || stationName.equals("")) {
            log.error(NULL_STATION_INPUT_EXC);
            throw new IllegalArgumentException(NULL_STATION_INPUT_EXC);
        }
        try {
            return stationDAO.findStationByName(stationName);
        } catch (DAOException e) {
            log.error("[StationService] An exception occurs while receiving Station by name. (name: {}). Exc: {}"
                    , stationName, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int countRecords() throws ServiceException {
        try {
            return stationDAO.countRecords();
        } catch (DAOException e) {
            log.error("[StationService] An exception occurs while counting stations. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
