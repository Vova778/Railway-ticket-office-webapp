package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleDAO {
    Connection getConnection();

    int insertSchedule(Schedule schedule) throws DAOException;

    void deleteSchedule(int scheduleId) throws DAOException;

    boolean updateSchedule(int scheduleId, Schedule schedule) throws DAOException;

    Optional<Schedule> findScheduleById(int scheduleId) throws DAOException;

    List<Schedule> findScheduleByTrain(Train train) throws DAOException;

    List<Schedule> findScheduleByDate(Date date) throws DAOException;

    List<Schedule> findAllSchedules() throws DAOException;
}
