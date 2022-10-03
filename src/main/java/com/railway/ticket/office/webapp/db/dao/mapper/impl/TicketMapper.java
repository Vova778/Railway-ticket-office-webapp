package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TicketMapper implements ObjectMapper<Ticket> {
    @Override
    public Ticket extractFromResultSet(ResultSet resultSet) throws SQLException {

        Ticket ticket = Ticket.newBuilder()
                .setId(resultSet.getInt(Fields.TICKET_ID))
                .setFare(resultSet.getInt(Fields.TICKET_FARE))
                .setStartingStationId(resultSet.getInt(Fields.TICKET_STARTING_STATION_ID))
                .setFinalStationId(resultSet.getInt(Fields.TICKET_FINAL_STATION_ID))
                .setDepartureTime(resultSet.getTimestamp(Fields.TICKET_DEPARTURE_TIME))
                .setArrivalTime(resultSet.getTimestamp(Fields.TICKET_ARRIVAL_TIME))
                .setTrainNumber(resultSet.getInt(Fields.TICKET_TRAIN_NUMBER))
                .setUserId(resultSet.getInt(Fields.TICKET_USER_ID))
                .setTicketStatus(Ticket.TicketStatus
                        .values()[resultSet.getInt(Fields.TICKET_STATUS_ID)-1])
                .build();

        return ticket;
    }

    @Override
    public Ticket toUnique(Map<String, Ticket> cache, Ticket object) {
        return null;
    }
}
