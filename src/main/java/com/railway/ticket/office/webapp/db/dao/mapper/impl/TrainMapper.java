package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.office.webapp.model.Train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TrainMapper implements ObjectMapper<Train> {
    @Override
    public Train extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Train> trainMapMap = new HashMap<>();
        Train train = new Train();

        train.setNumber(resultSet.getInt(Fields.TRAIN_NUMBER));
        train.setSeats(resultSet.getInt(Fields.TRAIN_SEATS));

        return train;
    }

}
