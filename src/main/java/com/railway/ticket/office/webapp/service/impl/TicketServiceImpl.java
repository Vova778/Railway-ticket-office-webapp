package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.TicketDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LogManager.getLogger(TicketServiceImpl.class);
    private static final String NULL_TICKET_DAO_EXC =
            "[TicketService] Can't create TicketService with null input TicketDAO";
    private static final String NULL_TICKET_INPUT_EXC =
            "[TicketService] Can't operate null input!";
    private static final String EXISTED_TICKET_EXC =
            "[TicketService] Ticket with given ID: [{}] is already registered!";
    private final TicketDAO ticketDAO;

    public TicketServiceImpl(TicketDAO ticketDAO){
        if (ticketDAO == null) {
            LOGGER.error(NULL_TICKET_DAO_EXC);
            throw new IllegalArgumentException(NULL_TICKET_DAO_EXC);
        }
        this.ticketDAO = ticketDAO;
    }

    @Override
    public void insert(Ticket ticket) throws ServiceException, FatalApplicationException {
        if (ticket == null) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            ticket.setId( ticketDAO.insertTicket(ticket));
        } catch (SQLException e) {
            LOGGER.error("[TicketService] SQLException while saving Schedule (id: {}). Exc: {}"
                    , ticket.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public void delete(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            ticketDAO.deleteTicket(scheduleId);
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while deleting Ticket. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(int ticketId, Ticket ticket) throws ServiceException {
        if (ticketId < 1 || ticket == null) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
           return   ticketDAO.updateTicket(ticketId, ticket);
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while updating Ticket. (id: {}). Exc: {}"
                    , ticketId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Ticket findTicketById(int ticketId) throws ServiceException {
        if (ticketId < 1) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            return ticketDAO.findTicketById(ticketId).get();
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while receiving Ticket. (id: {}). Exc: {}"
                    , ticketId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Ticket> findAll() throws ServiceException {
        try {
            return ticketDAO.findAllTickets();
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while receiving Tickets. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public List<Ticket> findTicketsByUserId(int userId) throws ServiceException {
        if (userId < 1) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            return ticketDAO.findTicketByUser(userId);
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while receiving Ticket. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Ticket> findTicketsByUserId(int userId, int offset) throws ServiceException {
        if (userId < 1) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            return ticketDAO.findTicketByUser(userId, offset);
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while receiving Ticket. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public int countRecords() throws ServiceException {
        try {
            return ticketDAO.countRecords();
        } catch (DAOException e) {
            LOGGER.error("[TicketService] An exception occurs while counting tickets. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
