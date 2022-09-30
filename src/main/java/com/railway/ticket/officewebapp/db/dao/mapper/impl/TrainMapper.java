package com.railway.ticket.officewebapp.db.dao.mapper.impl;

import com.railway.ticket.officewebapp.db.Fields;
import com.railway.ticket.officewebapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.officewebapp.model.Train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TrainMapper implements ObjectMapper<Train> {
    @Override
    public Train extractFromResultSet(ResultSet resultSet) throws SQLException {
        Map<String, Train> trainMapMap = new HashMap<>();
        Train train = new Train();

        train.setId(resultSet.getInt(Fields.TRAIN_ID));
        train.setSeats(resultSet.getInt(Fields.TRAIN_SEATS));

        return train;
    }

    @Override
    public Train toUnique(Map<String, Train> cache, Train object) {
        return null;
    }
}
