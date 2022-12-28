package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.ScheduleDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.ScheduleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * This class implements business logic for {@link Schedule}
 */
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger log = LogManager.getLogger(ScheduleServiceImpl.class);
    private static final String NULL_SCHEDULE_DAO_EXC =
            "[ScheduleService] Can't create ScheduleService with null input ScheduleDAO";
    private static final String NULL_SCHEDULE_INPUT_EXC =
            "[ScheduleService] Can't operate null input!";
    private static final String EXISTED_SCHEDULE_EXC =
            "[ScheduleService] Schedule with given ID: [{}] is already registered!";
    private final ScheduleDAO scheduleDAO;

    public ScheduleServiceImpl(ScheduleDAO scheduleDAO){
        if (scheduleDAO == null) {
            log.error(NULL_SCHEDULE_DAO_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_DAO_EXC);
        }
        this.scheduleDAO = scheduleDAO;
    }

    @Override
    public void insert(Schedule schedule) throws ServiceException {
        if (schedule == null) {
            log.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            schedule.setId( scheduleDAO.insert(schedule));;
            log.info("[ScheduleService] Schedule saved. (id: {})", schedule.getId());
        } catch (SQLException e) {
            log.error("[ScheduleService] SQLException while saving Schedule (id: {}). Exc: {}"
                    , schedule.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public void delete(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            log.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            scheduleDAO.delete(scheduleId);
        } catch (DAOException e) {
            log.error("[ScheduleService] An exception occurs while deleting Schedule. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Schedule schedule) throws ServiceException {
        if ( schedule == null || schedule.getId() < 1) {
            log.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
           return scheduleDAO.update(schedule);
        } catch (DAOException e) {
            log.error("[ScheduleService] An exception occurs while updating Schedule. (id: {}). Exc: {}"
                    , schedule.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Schedule findById(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            log.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            return scheduleDAO.findById(scheduleId);
        } catch (DAOException e) {
            log.error("[ScheduleService] An exception occurs while receiving Schedule. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schedule> findSchedulesByDate(Date date) throws ServiceException {
        if (date == null ) {
            log.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            return scheduleDAO.findScheduleByDate(date);
        } catch (DAOException e) {
            log.error("[ScheduleService] An exception occurs while receiving Schedules by date. (date: {}). Exc: {}"
                    , date, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schedule> findSchedulesByTrain(Train train) throws ServiceException {
        if (train == null ) {
            log.error(NULL_SCHEDULE_INPUT_EXC);
            throw new IllegalArgumentException(NULL_SCHEDULE_INPUT_EXC);
        }
        try {
            return scheduleDAO.findScheduleByTrain(train);
        } catch (DAOException e) {
            log.error("[ScheduleService] An exception occurs while receiving Schedules by train number. (train number: {}). Exc: {}"
                    , train.getNumber(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Schedule> findAll() throws ServiceException {
        try {
            return scheduleDAO.findAll();
        } catch (DAOException e) {
            log.error("[ScheduleService] An exception occurs while receiving Schedules. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
