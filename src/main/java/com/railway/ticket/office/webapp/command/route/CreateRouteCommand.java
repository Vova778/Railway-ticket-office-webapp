package com.railway.ticket.office.webapp.command.route;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.RouteService;
import com.railway.ticket.office.webapp.service.ScheduleService;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateRouteCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateRouteCommand.class);
    private static final String ADD_ROUTE_COMMAND = "[AddRouteCommand]";

    private final StationService stationService;
    private final ScheduleService scheduleService;
    private final RouteService routeService;

    public CreateRouteCommand(RouteService routeService, StationService stationService, ScheduleService scheduleService) {
        this.routeService = routeService;
        this.stationService = stationService;
        this.scheduleService = scheduleService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {

        int scheduleId;
        Route route;
        Station startingStation;
        Station finalStation;

        try {
            finalStation = stationService
                    .findByName(req.getParameter("finalStation"));
        } catch (ServiceException e) {
            log.error("An exception occurs while adding route." +
                            " The station with the name {} does not exist",
                    req.getParameter("finalStation"));
            throw new CommandException(e.getMessage(), e);
        }


        try {
            scheduleId = Integer.parseInt(
                    req.getParameter("scheduleId"));

            Schedule schedule = scheduleService
                    .findById(scheduleId);

            List<Route> routeList = routeService
                    .findRoutesByScheduleId(scheduleId);

            int stoppageNumber = routeList.size() + 1;
            Time arrivalTime = Time.valueOf(LocalTime.parse(req.getParameter("arrivalTime"),
                    DateTimeFormatter.ofPattern("HH:mm"))
                     );
            Time departureTime = Time.valueOf(LocalTime.parse(req.getParameter("departureTime"),
                    DateTimeFormatter.ofPattern("HH:mm")));
            int day;

            if(routeList.size()==0){
                day = 0;

                try {
                    startingStation = stationService
                            .findByName(req.getParameter("startingStation"));
                } catch (ServiceException e) {
                    log.error("An exception occurs while adding route." +
                                    " The station with the name {} does not exist",
                            req.getParameter("startingStation"));
                    throw new CommandException(e.getMessage(), e);
                }

            } else {
                Route previousRoute = routeList.get(routeList.size() - 1);
                day = previousRoute.getDay();

                departureTime
                        .setTime(departureTime.getTime()
                                + previousRoute.getArrivalTime().getTime());

                if (arrivalTime.before(departureTime))
                    day++;

                startingStation = previousRoute.getFinalStation();
            }

            route = Route.newBuilder()
                    .setStoppageNumber(stoppageNumber)
                    .setStartingStation(startingStation)
                    .setFinalStation(finalStation)
                    .setArrivalTime(arrivalTime)
                    .setDepartureTime(departureTime)
                    .setAvailableSeats(schedule.getTrain().getSeats())
                    .setDay(day)
                    .setPrice(Double.parseDouble(req.getParameter("price")))
                    .setTrain(schedule.getTrain())
                    .setSchedule(schedule)
                    .build();

            log.info("{} Route from view : {};", ADD_ROUTE_COMMAND, route);

            routeService.insert(route);

            log.info("{} Route was successfully added : {}", ADD_ROUTE_COMMAND, route);
        } catch (ServiceException e) {
            log.error("An exception occurs while adding route");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=schedule&scheduleId=" + scheduleId;
    }
}
