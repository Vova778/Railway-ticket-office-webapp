package com.railway.ticket.officewebapp.db.dao;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.model.Route;
import com.railway.ticket.officewebapp.model.Schedule;
import com.railway.ticket.officewebapp.model.Train;

import java.util.List;

public interface ScheduleDAO {
    void insertSchedule(Schedule schedule) throws DBException;
    void deleteSchedule(int scheduleId) throws DBException;
    void updateSchedule(int scheduleId, Schedule schedule) throws DBException;
    Schedule getScheduleById(int scheduleId) throws DBException;
    Schedule getScheduleByTrain(Train train) throws DBException;
    List<Schedule> findAllSchedule() throws DBException;
}
