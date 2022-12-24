package com.railway.ticket.office.webapp.command.ticket;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TicketDetailsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(TicketDetailsCommand.class);
    private static final String TICKET_DETAILS_COMMAND = "[TicketDetailsCommand]";
    private final RouteService routeService;

    public TicketDetailsCommand(RouteService routeService) {
        this.routeService = routeService;

    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {

        List<Route> routes;
        int ticketId;

        try {
            ticketId = Integer.parseInt(req.getParameter("ticketId"));
            routes = routeService.findRoutesByTicketId(ticketId);
            LOGGER.info("{} Routes by ticket id were found.", TICKET_DETAILS_COMMAND);

        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive routes by ticket id were found! " +
                    "An exception occurs: [{}]", TICKET_DETAILS_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        req.setAttribute("routes", routes);


        return "ticket_details.jsp";
    }
}
