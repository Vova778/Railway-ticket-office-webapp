package com.railway.ticket.office.webapp.db.dao.mapper.impl;

import com.railway.ticket.office.webapp.db.Fields;
import com.railway.ticket.office.webapp.db.dao.mapper.ObjectMapper;
import com.railway.ticket.office.webapp.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TicketMapper implements ObjectMapper<Ticket> {
    @Override
    public Ticket extractFromResultSet(ResultSet resultSet) throws SQLException {

        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getInt(Fields.TICKET_ID));
        ticket.setFare(resultSet.getInt(Fields.TICKET_FARE));
        ticket.setStartingStation(resultSet.getString(Fields.TICKET_STARTING_STATION_ID));
        ticket.setFinalStation(resultSet.getString(Fields.TICKET_FINAL_STATION_ID));
        ticket.setDepartureTime(resultSet.getTimestamp(Fields.TICKET_DEPARTURE_TIME));
        ticket.setArrivalTime(resultSet.getTimestamp(Fields.TICKET_ARRIVAL_TIME));
        ticket.setTrainNumber(resultSet.getInt(Fields.TICKET_TRAIN_NUMBER));
        ticket.setUserId(resultSet.getInt(Fields.TICKET_USER_ID));
        ticket.setTicketStatus(ticket.getById(resultSet.getInt(Fields.TICKET_STATUS_ID)));


        return ticket;
    }

    @Override
    public Ticket toUnique(Map<String, Ticket> cache, Ticket object) {
        return null;
    }
}
