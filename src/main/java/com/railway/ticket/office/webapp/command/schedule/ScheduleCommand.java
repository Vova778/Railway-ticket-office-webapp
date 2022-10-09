package com.railway.ticket.office.webapp.command.schedule;

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

public class ScheduleCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ScheduleCommand.class);
    private static final String SCHEDULE_COMMAND = "[ScheduleCommand]";
    private final RouteService routeService;

    public ScheduleCommand(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {

        List<Route> routes = null;
        try {
            int scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
            routes = routeService.findRoutesByScheduleId(scheduleId);
            LOGGER.info("{} Routes by schedule id were found.", SCHEDULE_COMMAND);
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive routes! " +
                    "An exception occurs: [{}]", SCHEDULE_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        req.setAttribute("routes", routes);

        return "schedule.jsp";
    }
}
