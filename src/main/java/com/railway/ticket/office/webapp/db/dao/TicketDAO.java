package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Ticket;

import java.sql.Connection;
import java.util.List;

public interface TicketDAO {
    Connection getConnection();

    void insertTicket(Ticket ticket) throws DAOException;

    void deleteTicket(int ticketId)throws DAOException;

    void updateTicket(int ticketId, Ticket ticket) throws DAOException;

    Ticket findTicketById(int ticketId) throws DAOException;

    List<Ticket> findTicketByUser(int userId) throws DAOException;

    List<Ticket> findAllTickets() throws DAOException;


}
