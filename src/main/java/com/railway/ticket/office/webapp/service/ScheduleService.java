package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.Date;
import java.util.List;

public interface ScheduleService{
    void insert (Schedule schedule) throws ServiceException;

    void delete (int scheduleId) throws ServiceException;

    boolean update(Schedule schedule) throws ServiceException;

    Schedule findById(int scheduleId) throws ServiceException;

    List<Schedule> findSchedulesByDate (Date date) throws ServiceException;

    List<Schedule> findSchedulesByTrain (Train train) throws ServiceException;

    List<Schedule> findAll () throws ServiceException;
}
