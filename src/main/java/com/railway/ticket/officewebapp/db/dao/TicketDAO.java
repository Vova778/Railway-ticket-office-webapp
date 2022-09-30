package com.railway.ticket.officewebapp.db.dao;

import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.model.Ticket;

import java.util.List;

public interface TicketDAO {
    void insertTicket(Ticket ticket) throws DBException;

    void deleteTicket(int ticketId)throws DBException;

    void updateTicket(int ticketId, Ticket ticket) throws DBException;

    Ticket getTicketById(int ticketId) throws DBException;

    List<Ticket> findAllTickets() throws DBException;
}
