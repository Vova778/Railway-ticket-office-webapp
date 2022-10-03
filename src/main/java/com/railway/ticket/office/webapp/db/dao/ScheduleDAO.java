package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface ScheduleDAO {
    Connection getConnection();

    void insertSchedule(Schedule schedule) throws DAOException;

    void deleteSchedule(int scheduleId) throws DAOException;

    void updateSchedule(int scheduleId, Schedule schedule) throws DAOException;

    Schedule findScheduleById(int scheduleId) throws DAOException;

    List<Schedule> findScheduleByTrain(Train train) throws DAOException;

    List<Schedule> findScheduleByDate(Date date) throws DAOException;

    List<Schedule> findAllSchedules() throws DAOException;
}
