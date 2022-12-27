package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;

import java.util.List;

public interface StationService {
    void insert (Station station) throws ServiceException, FatalApplicationException;

    void delete (int stationId) throws ServiceException;

    boolean update(Station station) throws ServiceException;

    Station findById(int stationId) throws ServiceException;

    Station findByName(String stationName) throws ServiceException;

    List<Station> findAll() throws ServiceException;

    List<Station> findAll(int offset) throws ServiceException;

    int countRecords() throws ServiceException;
}
