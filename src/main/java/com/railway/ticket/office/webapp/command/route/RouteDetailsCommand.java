package com.railway.ticket.office.webapp.command.route;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.model.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class RouteDetailsCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        HttpSession session = req.getSession();
        int trainNumber = Integer.parseInt(req.getParameter("trainNumber"));

        Map.Entry<Route, List<Route>> route
                = ((Map<Route, List<Route>>) session.getAttribute("routes"))
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getTrain().getNumber() == trainNumber)
                .findFirst()
                .get();

        req.setAttribute("routes", route.getValue());


        return "ticket_details.jsp";
    }
}
