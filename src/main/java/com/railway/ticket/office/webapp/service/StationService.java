package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;

import java.util.List;

public interface StationService {
    void insert (Station station) throws ServiceException, FatalApplicationException;

    void delete (int stationId) throws ServiceException;

    void update (int stationId, Station station) throws ServiceException;

    Station findStationById (int stationId) throws ServiceException;

    Station findStationByName (String stationName) throws ServiceException;

    List<Station> findAll() throws ServiceException;

    List<Station> findAll(int offset) throws ServiceException;

    int countRecords() throws ServiceException;
}
