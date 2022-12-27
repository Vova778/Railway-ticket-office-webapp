package com.railway.ticket.office.webapp.db.dao;

import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Ticket;

import java.sql.Connection;
import java.util.List;

public interface TicketDAO {
    Connection getConnection();

    int insert(Ticket ticket) throws DAOException;

    void delete(int ticketId)throws DAOException;

    boolean update(Ticket ticket) throws DAOException;

    Ticket findById(int ticketId) throws DAOException;

    List<Ticket> findTicketByUser(int userId) throws DAOException;

    List<Ticket> findTicketByUser(int userId, int offset) throws DAOException;

    List<Ticket> findAll() throws DAOException;

    int countRecords() throws DAOException;
}
