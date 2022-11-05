package com.railway.ticket.office.webapp.command.ticket;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.RouteService;
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

public class BookTicketCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(BookTicketCommand.class);
    private static final String BOOK_TICKET_COMMAND = "[BookTicketCommand]";

    private final TicketService ticketService;
    private final RouteService routeService;

    public BookTicketCommand(TicketService ticketService, RouteService routeService) {
        this.ticketService = ticketService;
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        HttpSession session = req.getSession();
        try {

            List<Route> routes = (List<Route>) session.getAttribute("routes");
            Route route = routes.get(Integer.parseInt(req.getParameter("routeId")));

            User user = (User) session.getAttribute("user");

            Ticket ticket = new Ticket();

            Timestamp arrivalTime = Timestamp.valueOf( route.getSchedule().getDate() +" "+ route.getArrivalTime());
            Timestamp departureTime = Timestamp.valueOf( route.getSchedule().getDate() +" "+ route.getDepartureTime());

            ticket.setArrivalTime(arrivalTime);
            ticket.setDepartureTime(departureTime);
            ticket.setFare(route.getPrice());
            ticket.setStartingStation(route.getStartingStation().getName());
            ticket.setFinalStation(route.getFinalStation().getName());
            ticket.setTrainNumber(route.getTrain().getNumber());
            ticket.setUserId(user.getId());
            ticket.setTicketStatus(Ticket.TicketStatus.QUEUED);
            ticket.setRoutes(routeService.findRoutesBetweenStations(
                    route.getSchedule(),
                    route.getStartingStation(),
                    route.getFinalStation()));


            LOGGER.info("{} Ticket from view : {};"
                    , BOOK_TICKET_COMMAND, ticket);

            ticketService.insert(ticket);

            ticket.getRoutes()
                    .forEach((r) -> r.setAvailableSeats(r.getAvailableSeats()-1));

            routeService.updateTicketRoutes(ticket);

            LOGGER.info("{} Ticket was successfully added : {}"
                    , BOOK_TICKET_COMMAND, ticket);

        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while adding Ticket");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=basket";
    }
}
