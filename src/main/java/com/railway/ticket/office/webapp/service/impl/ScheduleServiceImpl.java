package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.ScheduleDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.ScheduleService;
import com.railway.ticket.office.webapp.utils.db.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger LOGGER = LogManager.getLogger(ScheduleServiceImpl.class);
    private static final String NULL_SCHEDULE_DAO_EXC =
            "[ScheduleService] Can't create ScheduleService with null input ScheduleDAO";
    private static final String NULL_SCHEDULE_INPUT_EXC =
            "[ScheduleService] Can't operate null input!";
    private static final String EXISTED_SCHEDULE_EXC =
            "[ScheduleService] Schedule with given ID: [{}] is already registered!";
    private final ScheduleDAO scheduleDAO;

    public ScheduleServiceImpl(ScheduleDAO scheduleDAO){
        if (scheduleDAO == null) {
            LOGGER.error(NULL_SCHEDULE_DAO_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_DAO_EXC);
        }
        this.scheduleDAO = scheduleDAO;
    }

    @Override
    public void insert(Schedule schedule) throws ServiceException, FatalApplicationException {
        if (schedule == null) {
            LOGGER.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            checkAndSave(schedule);
        } catch (SQLException e) {
            LOGGER.error("[ScheduleService] SQLException while saving Schedule (id: {}). Exc: {}"
                    , schedule.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void checkAndSave(Schedule schedule) throws ServiceException, SQLException {
        scheduleDAO.getConnection().setAutoCommit(false);
        try {
            if (scheduleDAO.findScheduleById(schedule.getId()).getId() != 0) {
                DBUtils.rollback(scheduleDAO.getConnection());
                LOGGER.error(EXISTED_SCHEDULE_EXC
                        , schedule.getId());
                throw new ServiceException(EXISTED_SCHEDULE_EXC);
            }
            scheduleDAO.insertSchedule(schedule);
            scheduleDAO.getConnection().commit();
            scheduleDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[ScheduleService] Schedule saved. (id: {})", schedule.getId());
        } catch (DAOException e) {
            scheduleDAO.getConnection().rollback();
            LOGGER.error("[ScheduleService] Connection rolled back while saving Schedule. (id: {}). Exc: {}"
                    , schedule.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            LOGGER.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            scheduleDAO.deleteSchedule(scheduleId);
        } catch (DAOException e) {
            LOGGER.error("[ScheduleService] An exception occurs while deleting Schedule. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(int scheduleId, Schedule schedule) throws ServiceException {
        if (scheduleId < 1 || schedule == null) {
            LOGGER.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            scheduleDAO.updateSchedule(scheduleId, schedule);
        } catch (DAOException e) {
            LOGGER.error("[ScheduleService] An exception occurs while updating Schedule. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Schedule findScheduleById(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            LOGGER.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            return scheduleDAO.findScheduleById(scheduleId);
        } catch (DAOException e) {
            LOGGER.error("[ScheduleService] An exception occurs while receiving Schedule. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schedule> findSchedulesByDate(Date date) throws ServiceException {
        if (date == null ) {
            LOGGER.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            return scheduleDAO.findScheduleByDate(date);
        } catch (DAOException e) {
            LOGGER.error("[ScheduleService] An exception occurs while receiving Schedules by date. (date: {}). Exc: {}"
                    , date, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schedule> findSchedulesByTrain(Train train) throws ServiceException {
        if (train == null ) {
            LOGGER.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            return scheduleDAO.findScheduleByTrain(train);
        } catch (DAOException e) {
            LOGGER.error("[ScheduleService] An exception occurs while receiving Schedules by train number. (train number: {}). Exc: {}"
                    , train.getNumber(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schedule> findAll() throws ServiceException {
        try {
            return scheduleDAO.findAllSchedules();
        } catch (DAOException e) {
            LOGGER.error("[ScheduleService] An exception occurs while receiving Schedules. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
