package com.railway.ticket.office.webapp.command.ticket;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.service.RouteService;
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelTicketCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CancelTicketCommand.class);
    private static final String BOOK_TICKET_COMMAND = "[BookTicketCommand]";

    private final TicketService ticketService;
    private final RouteService routeService;


    public CancelTicketCommand(TicketService ticketService, RouteService routeService) {
        this.ticketService = ticketService;
        this.routeService = routeService;
    }


    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        try {
            int ticketId = Integer.parseInt(req.getParameter("ticketId"));
            Ticket ticket = ticketService.findTicketById(ticketId);

            if (ticket.getTicketStatus().getId() == 1) {

                ticket.setTicketStatus(Ticket.TicketStatus.CANCELED);
                ticketService.update(ticketId, ticket);

                LOGGER.info("{} Ticket from view : {};"
                        , BOOK_TICKET_COMMAND, ticket);

                LOGGER.info("{} Ticket was successfully cancelled : {}"
                        , BOOK_TICKET_COMMAND, ticket);
            }

        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while adding Ticket");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=basket";
    }
}

