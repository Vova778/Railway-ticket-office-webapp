package com.railway.ticket.office.webapp.db.dao.impl;

import com.railway.ticket.office.webapp.db.Constants;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.ScheduleMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.db.dao.ScheduleDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleDAOImpl implements ScheduleDAO {

    private final Connection con;
    private static final Logger LOGGER = LogManager.getLogger(ScheduleDAOImpl.class);
    private final ScheduleMapper scheduleMapper = new ScheduleMapper();

    public ScheduleDAOImpl(Connection con) {
        this.con = con;
    }


    @Override
    public Connection getConnection() {
        return con;
    }

    @Override
    public void insertSchedule(Schedule schedule) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.SCHEDULE_INSERT_SCHEDULE,
                            Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;

            preparedStatement.setDate(k++,schedule.getDate());
            preparedStatement.setInt(k,schedule.getTrainId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                schedule.setId(resultSet.getInt(1));
                LOGGER.info("Schedule : {} was inserted successfully", schedule);
            }


        } catch (SQLException e) {
            LOGGER.error("Schedule : [{}] was not inserted. An exception occurs : {}",
                    schedule, e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while creating Schedule" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSchedule(int scheduleId) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.SCHEDULE_DELETE_SCHEDULE)) {

            preparedStatement.setInt(1,scheduleId);

            int removedRow = preparedStatement.executeUpdate();

            if(removedRow>0){
                LOGGER.info("Schedule with ID : {} was removed successfully", scheduleId);
            }

        } catch (SQLException e) {
            LOGGER.error("Schedule with ID : [{}] was not removed. An exception occurs : {}",
                    scheduleId, e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while removing Schedule" + e.getMessage(), e);
        }
    }

    @Override
    public void updateSchedule(int scheduleId, Schedule schedule) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.SCHEDULE_UPDATE_SCHEDULE)) {

            int k = 1;

            preparedStatement.setDate(k++,schedule.getDate());
            preparedStatement.setInt(k++,schedule.getTrainId());
            preparedStatement.setInt(k,scheduleId);

            int updatedRow = preparedStatement.executeUpdate();
            if(updatedRow>0){
                LOGGER.info("Schedule with ID : {} was updated successfully", scheduleId);
            }

        } catch (SQLException e) {
            LOGGER.error("Schedule with ID : [{}] was not updated. An exception occurs : {}",
                    scheduleId, e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while updating Schedule" + e.getMessage(), e);
        }
    }

    @Override
    public Schedule findScheduleById(int scheduleId) throws DAOException {
        Optional<Schedule> schedule = Optional.empty();

        try(PreparedStatement preparedStatement = con.prepareStatement(Constants.SCHEDULE_GET_SCHEDULE_BY_ID)) {

            preparedStatement.setInt(1,scheduleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    schedule = Optional.ofNullable(scheduleMapper
                            .extractFromResultSet(resultSet));
                }
            }

        }
        catch (SQLException e) {
            LOGGER.error("Schedule with Id : [{}] was not found. An exception occurs : {}",
                    scheduleId, e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while loading Schedule by id" + e.getMessage(), e);
        }
        return schedule.get();
    }

    @Override
    public List<Schedule> findScheduleByTrain(Train train) throws DAOException {
        List<Schedule> schedules = new ArrayList<>();


        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.SCHEDULE_GET_SCHEDULE_BY_TRAIN_ID)) {

            preparedStatement.setInt(1,train.getNumber());

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    schedules.add(scheduleMapper.extractFromResultSet(resultSet));
                }
            }

        }
        catch (SQLException e) {
            LOGGER.error("Schedule with train Id : [{}] was not found. An exception occurs : {}",
                    train.getNumber(), e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while loading Schedule by train Id" + e.getMessage(), e);
        }
        return schedules;
    }

    @Override
    public List<Schedule> findScheduleByDate(Date date) throws DAOException {
        List<Schedule> schedules = new ArrayList<>();

        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.SCHEDULE_GET_SCHEDULE_BY_DATE)) {

            preparedStatement.setDate(1,date);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    schedules.add(scheduleMapper.extractFromResultSet(resultSet));
                }
            }

        }
        catch (SQLException e) {
            LOGGER.error("Schedule with Date : [{}] was not found. An exception occurs : {}",
                    date, e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while loading Schedule by date" + e.getMessage(), e);
        }
        return schedules;
    }

    @Override
    public List<Schedule> findAllSchedules() throws DAOException {
        List<Schedule> schedules = new ArrayList<>();


        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.SCHEDULE_GET_ALL_SCHEDULE)
        ) {

            while (resultSet.next()){
                schedules.add(scheduleMapper.extractFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            LOGGER.error("Schedules were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[ScheduleDAO] exception while reading all schedules" + e.getMessage(), e);
        }

        return schedules;
    }
}
