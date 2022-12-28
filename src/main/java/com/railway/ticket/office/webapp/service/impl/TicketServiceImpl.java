package com.railway.ticket.office.webapp.service.impl;

import com.railway.ticket.office.webapp.db.dao.TicketDAO;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * This class implements business logic for {@link Ticket}
 */
public class TicketServiceImpl implements TicketService {

    private static final Logger log = LogManager.getLogger(TicketServiceImpl.class);
    private static final String NULL_TICKET_DAO_EXC =
            "[TicketService] Can't create TicketService with null input TicketDAO";
    private static final String NULL_TICKET_INPUT_EXC =
            "[TicketService] Can't operate null input!";
    private final TicketDAO ticketDAO;

    public TicketServiceImpl(TicketDAO ticketDAO){
        if (ticketDAO == null) {
            log.error(NULL_TICKET_DAO_EXC);
            throw new IllegalArgumentException(NULL_TICKET_DAO_EXC);
        }
        this.ticketDAO = ticketDAO;
    }

    @Override
    public void insert(Ticket ticket) throws ServiceException {
        if (ticket == null) {
            log.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            ticket.setId( ticketDAO.insert(ticket));
        } catch (SQLException e) {
            log.error("[TicketService] SQLException while saving Schedule (id: {}). Exc: {}"
                    , ticket.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public void delete(int scheduleId) throws ServiceException {
        if (scheduleId < 1) {
            log.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            ticketDAO.delete(scheduleId);
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while deleting Ticket. (id: {}). Exc: {}"
                    , scheduleId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Ticket ticket) throws ServiceException {
        if (ticket == null || ticket.getId() < 1 ) {
            log.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
           return   ticketDAO.update(ticket);
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while updating Ticket. (id: {}). Exc: {}"
                    , ticket.getId(), e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Ticket findById(int ticketId) throws ServiceException {
        if (ticketId < 1) {
            log.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            return ticketDAO.findById(ticketId);
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while receiving Ticket. (id: {}). Exc: {}"
                    , ticketId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Ticket> findAll() throws ServiceException {
        try {
            return ticketDAO.findAll();
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while receiving Tickets. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public List<Ticket> findTicketsByUserId(int userId) throws ServiceException {
        if (userId < 1) {
            log.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            return ticketDAO.findTicketByUser(userId);
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while receiving Ticket. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<Ticket> findTicketsByUserId(int userId, int offset) throws ServiceException {
        if (userId < 1) {
            log.error(NULL_TICKET_INPUT_EXC);
            throw new IllegalArgumentException(NULL_TICKET_INPUT_EXC);
        }
        try {
            return ticketDAO.findTicketByUser(userId, offset);
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while receiving Ticket. (id: {}). Exc: {}"
                    , userId, e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * @return all {@link Ticket} records in database
     */
    @Override
    public int countRecords() throws ServiceException {
        try {
            return ticketDAO.countRecords();
        } catch (DAOException e) {
            log.error("[TicketService] An exception occurs while counting tickets. Exc: {}",
                    e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
