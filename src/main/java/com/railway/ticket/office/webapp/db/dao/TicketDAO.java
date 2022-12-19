package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Ticket;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface TicketDAO {
    Connection getConnection();

    int insertTicket(Ticket ticket) throws DAOException;

    void deleteTicket(int ticketId)throws DAOException;

    boolean updateTicket(int ticketId, Ticket ticket) throws DAOException;

    Optional<Ticket> findTicketById(int ticketId) throws DAOException;

    List<Ticket> findTicketByUser(int userId) throws DAOException;

    List<Ticket> findTicketByUser(int userId, int offset) throws DAOException;

    List<Ticket> findAllTickets() throws DAOException;

    int countRecords() throws DAOException;
}
