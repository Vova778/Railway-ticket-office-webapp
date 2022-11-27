package com.railway.ticket.office.webapp.command.route;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.RouteService;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddRouteFormCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddRouteFormCommand.class);
    private static final String ROUTE_FORM_COMMAND = "[RouteFormCommand]";
    private final StationService stationService;
    private final RouteService routeService;

    public AddRouteFormCommand(StationService stationService, RouteService routeService) {
        this.stationService = stationService;
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws CommandException, FatalApplicationException {
        List<Route> routes;
        List<Station> stations;
        try {
            routes = routeService.findAll();
            stations = stationService.findAll();
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive stations! " +
                    "An exception occurs: [{}]", ROUTE_FORM_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("stations", stations);
        request.setAttribute("routes", routes);
        return "create_route.jsp";
    }
}
