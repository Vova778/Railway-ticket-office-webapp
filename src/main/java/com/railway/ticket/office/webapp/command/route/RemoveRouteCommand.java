package com.railway.ticket.office.webapp.command.route;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.service.RouteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveRouteCommand implements Command {
    private static final Logger log = LogManager.getLogger(RemoveRouteCommand.class);
    private static final String REMOVE_ROUTE_COMMAND = "[RemoveRouteCommand]";
    private final RouteService routeService;

    public RemoveRouteCommand(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        try {
            routeService.delete(Integer.parseInt(request.getParameter("routeId")));

            return "controller?command=schedule&scheduleId=" + request.getParameter("scheduleId");
        } catch (ServiceException e) {
            log.error("{} Route wasn't removed. An exception occurs: [{}]",
                    REMOVE_ROUTE_COMMAND , e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
    }

}
