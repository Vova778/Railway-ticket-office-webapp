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
import java.util.List;

public class AddRouteCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddRouteCommand.class);
    private static final String ADD_ROUTE_COMMAND = "[AddRouteCommand]";

    private final StationService stationService;
    private final RouteService routeService;

    public AddRouteCommand(RouteService routeService, StationService stationService) {
        this.routeService = routeService;
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {

        int scheduleId;
        Route route;
        String destinationStationName = "";
        Station destinationStation;
        try {
            destinationStationName = req.getParameter("destinationPoint");
            destinationStation = stationService.findStationByName(destinationStationName);

        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while adding route. The station with the name {} does not exist",
                    destinationStationName);
            throw new CommandException(e.getMessage(), e);
        }

        try {

            scheduleId = Integer.parseInt(req.getParameter("scheduleId"));
            Time arrivalTime = Time.valueOf(req.getParameter("arrivalTime") + ":00");
            Time departureTime = Time.valueOf(req.getParameter("departureTime") + ":00");

            List<Route> routeList = routeService.findRoutesByScheduleId(scheduleId);
            int stoppageNumber = routeList.size() + 1;

            Route previousRoute = routeList.get(routeList.size() - 1);
            int day = previousRoute.getDay();
            if (arrivalTime.before(departureTime))
                day++;
            route = Route.newBuilder()
                    .setStoppageNumber(stoppageNumber)
                    .setStartingStation(previousRoute.getFinalStation())
                    .setFinalStation(destinationStation)
                    .setArrivalTime(arrivalTime)
                    .setDepartureTime(departureTime)
                    .setAvailableSeats(previousRoute.getTrain().getSeats())
                    .setDay(day)
                    .setPrice(Double.parseDouble(req.getParameter("price")))
                    .setTrain(previousRoute.getTrain())
                    .setScheduleId(scheduleId)
                    .build();

            LOGGER.info("{} Route from view : {};", ADD_ROUTE_COMMAND, route);

            routeService.insert(route);
            LOGGER.info("{} Route was successfully added : {}", ADD_ROUTE_COMMAND, route);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while adding route");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=schedule&scheduleId=" + scheduleId;
    }
}
