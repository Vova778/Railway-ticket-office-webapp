package com.railway.ticket.office.webapp.command.route;

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
import java.util.ArrayList;
import java.util.List;

public class AllRoutesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AllRoutesCommand.class);
    private static final String ALL_ROUTES_COMMAND = "[AllRoutesCommand]";
    private final RouteService routeService;

    public AllRoutesCommand(RouteService routeService) {
        this.routeService = routeService;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException, FatalApplicationException {
        int page;
        List<Route> routes;
        int countPages;
        if (request.getParameter("page") == null
                || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
            routes = routeService.findAll((page-1)*10);
            LOGGER.info("{} Routes found.", ALL_ROUTES_COMMAND);
            countPages = routeService.countRecords() / 10 + 1;
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive routes! " +
                    "An exception occurs: [{}]", ALL_ROUTES_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("routes", routes);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++)  pages.add(i);
        request.setAttribute("pages", pages);
        return "routes.jsp";
    }
}
