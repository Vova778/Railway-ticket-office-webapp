package com.railway.ticket.office.webapp.command.ticket;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class BookTicketCommand implements Command {

    private static final Logger log = LogManager.getLogger(BookTicketCommand.class);
    private static final String BOOK_TICKET_COMMAND = "[BookTicketCommand]";

    private final TicketService ticketService;

    public BookTicketCommand(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        HttpSession session = req.getSession();
        try {
            int trainNumber = Integer.parseInt(req.getParameter("trainNumber"));

            Map.Entry<Route, List<Route>> route
                    =((Map<Route, List<Route>>) session.getAttribute("routes"))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().getTrain().getNumber() == trainNumber)
                    .findFirst()
                    .get();
            if(route.getKey().getAvailableSeats()>0) {
                User user = (User) session.getAttribute("user");

                Ticket ticket = new Ticket();

                Timestamp arrivalTime =
                        Timestamp.valueOf(route.getKey().getSchedule().getDate()
                                + " " + route.getKey().getArrivalTime());
                Timestamp departureTime
                        = Timestamp.valueOf(route.getKey().getSchedule().getDate()
                        + " " + route.getKey().getDepartureTime());

                ticket.setArrivalTime(arrivalTime);
                ticket.setDepartureTime(departureTime);
                ticket.setFare(route.getKey().getPrice());
                ticket.setStartingStation(route.getKey().getStartingStation().getName());
                ticket.setFinalStation(route.getKey().getFinalStation().getName());
                ticket.setTrainNumber(route.getKey().getTrain().getNumber());
                ticket.setUserId(user.getId());
                ticket.setTicketStatus(Ticket.TicketStatus.QUEUED);
                ticket.setRoutes(route.getValue());

                log.info("{} Ticket from view : {};"
                        , BOOK_TICKET_COMMAND, ticket);

                ticketService.insert(ticket);

                log.info("{} Ticket was successfully added : {}"
                        , BOOK_TICKET_COMMAND, ticket);
            }
        } catch (ServiceException e) {
            log.error("An exception occurs while adding Ticket");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=basket";
    }
}
