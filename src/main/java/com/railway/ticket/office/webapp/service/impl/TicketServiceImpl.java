package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.TicketDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.service.TicketService;
import com.railway.ticket.office.webapp.utils.db.DBUtils;
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
            checkAndSave(ticket);
        } catch (SQLException e) {
            LOGGER.error("[TicketService] SQLException while saving Schedule (id: {}). Exc: {}"
                    , ticket.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void checkAndSave(Ticket ticket) throws ServiceException, SQLException {
        ticketDAO.getConnection().setAutoCommit(false);
        try {
            if (ticketDAO.findTicketById(ticket.getId()).getId() != 0) {
                DBUtils.rollback(ticketDAO.getConnection());
                LOGGER.error(EXISTED_TICKET_EXC
                        , ticket.getId());
                throw new ServiceException(EXISTED_TICKET_EXC);
            }
            ticketDAO.insertTicket(ticket);
            ticketDAO.getConnection().commit();
            ticketDAO.getConnection().setAutoCommit(true);
            LOGGER.info("[TicketService] Ticket saved. (id: {})", ticket.getId());
        } catch (DAOException e) {
            ticketDAO.getConnection().rollback();
            LOGGER.error("[TicketService] Connection rolled back while saving Ticket. (id: {}). Exc: {}"
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
    public void update(int ticketId, Ticket ticket) throws ServiceException {
        if (ticketId < 1 || ticket == null) {
            LOGGER.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            ticketDAO.updateTicket(ticketId, ticket);
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
            return ticketDAO.findTicketById(ticketId);
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
}
