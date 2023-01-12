package com.railway.ticket.office.webapp.command.user;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Ticket;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UserTicketsCommand implements Command {
    private static final Logger log = LogManager.getLogger(UserTicketsCommand.class);
    private final TicketService ticketService;
    private static final String USER_TICKETS_COMMAND = "[UserTicketsCommand]";


    public UserTicketsCommand(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException, FatalApplicationException {
        HttpSession session = request.getSession();
        int page;

        if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
            page = 0;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Ticket> tickets;
        User user = (User) session.getAttribute("user");
        int countPages;
        try {
            tickets = ticketService.findTicketsByUserId(user.getId(), page);
            log.info("{} Tickets were found.", USER_TICKETS_COMMAND);

            countPages = ticketService.countRecords() / 10 + 1;

        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

        request.setAttribute("tickets", tickets);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            pages.add(i);
        }
        request.setAttribute("pages", pages);
        return "basket.jsp";
    }
}
