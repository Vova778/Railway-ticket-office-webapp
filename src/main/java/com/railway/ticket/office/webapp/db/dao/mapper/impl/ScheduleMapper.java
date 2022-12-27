package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleMapper implements ObjectMapper<Schedule> {
    @Override
    public Schedule extractFromResultSet(ResultSet resultSet) throws SQLException {

        Schedule schedule = new Schedule();
        schedule.setId(resultSet.getInt(Fields.SCHEDULE_ID));
        schedule.setDate(resultSet.getDate(Fields.SCHEDULE_DATE));
        schedule.setTrain(new Train(
                resultSet.getInt(Fields.TRAIN_NUMBER),
                resultSet.getInt(Fields.TRAIN_SEATS)));

        return schedule;
    }

}
