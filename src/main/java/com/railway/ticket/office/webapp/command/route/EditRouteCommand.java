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
import java.sql.Time;
import java.util.Optional;

public class EditRouteCommand implements Command {

    private static final Logger LOGGER
            = LogManager.getLogger(EditRouteCommand.class);
    private final RouteService routeService;
    private final StationService stationService;

    public EditRouteCommand(RouteService routeService, StationService stationService ) {
        this.routeService = routeService;
        this.stationService = stationService;
    }
    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        int scheduleId;
        try {
            scheduleId = Integer.parseInt(req.getParameter("scheduleId"));

            Optional<Station> startingStation
                    = stationService.findStationByName(req.getParameter("startingStation"));

            Optional<Station> finalStation
                    = stationService.findStationByName(req.getParameter("finalStation"));
            Route updated = routeService
                    .findRouteById(
                            Integer.parseInt(req.getParameter("routeId")));

            Time arrivalTime = Time.valueOf(req.getParameter("arrivalTime"));
            Time departureTime = Time.valueOf(req.getParameter("departureTime"));

            startingStation.ifPresent(updated::setStartingStation);
            finalStation.ifPresent(updated::setFinalStation);
            updated.setArrivalTime(arrivalTime);
            updated.setDepartureTime(departureTime);
            updated.setPrice(Double.parseDouble(req.getParameter("price")));

            routeService.update(updated.getId(), updated);
            return "controller?command=schedule&scheduleId="+scheduleId;
        } catch (ServiceException e) {
            LOGGER.error("[EditStationCommand] Can't update station");
            throw new CommandException(e.getMessage(), e);
        }
    }

}
