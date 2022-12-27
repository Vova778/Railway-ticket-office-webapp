package com.railway.ticket.office.webapp.service;

import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Ticket;

import java.util.List;

public interface TicketService {

    void insert(Ticket ticket) throws ServiceException, FatalApplicationException;

    boolean update(Ticket ticket) throws ServiceException;

    void delete(int ticketId) throws ServiceException;

    List<Ticket> findAll() throws ServiceException;

    Ticket findById(int ticketId) throws ServiceException;

    List<Ticket> findTicketsByUserId (int userId) throws ServiceException;

    List<Ticket> findTicketsByUserId (int userId, int offset) throws ServiceException;

    int countRecords() throws ServiceException;
}
