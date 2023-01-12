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
import javax.servlet.http.HttpSession;
import java.util.List;

public class EditRouteFormCommand implements Command {
    private static final Logger log
            = LogManager.getLogger(EditRouteFormCommand.class);
    private final RouteService routeService;
    private final StationService stationService;

    public EditRouteFormCommand(RouteService routeService, StationService stationService) {
        this.routeService = routeService;
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws CommandException, FatalApplicationException {

        int id=0;
        Route route;
        List<Route> routes;
        HttpSession session = req.getSession();
        List<Station> stations;

        try {
            stations = stationService.findAll();
        } catch (ServiceException e) {
            log.error("[EditRouteFormCommand] Can't receive stations! " +
                    "An exception occurs: [{}]", e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }

        try {
            routes = routeService.findAll();
        } catch (ServiceException e) {
            log.error("[EditRouteFormCommand] Can't receive routes");
            throw new CommandException(e.getMessage(), e);
        }

        try {
            id = Integer.parseInt(req.getParameter("routeId"));
            route = routeService.findById(id);
        } catch (ServiceException e) {
            log.error("[EditRouteFormCommand] Can't receive route by id:[{}]", id);
            throw new CommandException(e.getMessage(), e);
        }
        session.setAttribute("stations", stations);
        req.setAttribute("routes", routes);
        req.setAttribute("route", route);
        return "edit_route.jsp";
    }
}
