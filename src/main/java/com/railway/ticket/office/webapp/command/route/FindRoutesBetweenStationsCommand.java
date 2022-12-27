package com.railway.ticket.office.webapp.command.route;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.RouteService;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class FindRoutesBetweenStationsCommand implements Command {
    private static final Logger log = LogManager.getLogger(FindRoutesBetweenStationsCommand.class);
    private static final String FIND_ROUTE_COMMAND = "[FindRouteCommand]";

    private final StationService stationService;
    private final RouteService routeService;

    public FindRoutesBetweenStationsCommand(StationService stationService,
                                            RouteService routeService) {
        this.stationService = stationService;
        this.routeService = routeService;
    }


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse resp) throws CommandException, FatalApplicationException {
        List<Route> routes = new LinkedList<>();
        HttpSession session = request.getSession();
        Map<Schedule,List<Route>> schedules;
        int countPages;

        try {
            Date date = Date.valueOf(request.getParameter("date"));
            Station startingStation = stationService.findByName(
                    request.getParameter("startingStation"));
            Station finalStation = stationService.findByName(
                    request.getParameter("finalStation"));
            schedules = routeService
                    .findRoutesBetweenStations(date, finalStation, startingStation )
                    .stream()
                    .collect(Collectors.groupingBy(Route::getSchedule));

            int id =0;

            for ( Map.Entry<Schedule, List<Route>> schedule :
                    schedules.entrySet()) {

                Route route = new Route();
                route.setStartingStation(startingStation);
                route.setFinalStation(finalStation);

                route.setDepartureTime(schedule.getValue().get(0).getDepartureTime());
                route.setTrain(schedule.getValue().get(0).getTrain());
                route.setSchedule(schedule.getValue().get(0).getSchedule());
                route.setArrivalTime(
                        schedule.getValue()
                                .get(schedule.getValue().size()-1)
                                .getArrivalTime());


                double price = schedule.getValue()
                        .stream()
                        .mapToDouble(Route::getPrice)
                        .sum();
                int availableSeats = schedule.getValue()
                        .stream()
                        .mapToInt(Route::getAvailableSeats)
                        .min()
                        .getAsInt();

                route.setId(id++);
                route.setTravelTime(new Time(route.getArrivalTime().getTime()
                        -route.getDepartureTime().getTime() - 7200000 ));
                route.setAvailableSeats(availableSeats);
                route.setPrice(price);
                routes.add(route);

            }
            session.setAttribute("routes", routes);
            log.info("{} Routes between stations were found.", FIND_ROUTE_COMMAND);
            countPages = schedules.size() / 10 + 1;
        } catch (ServiceException e) {
            log.error("{} Can't receive routes! " +
                    "An exception occurs: [{}]", FIND_ROUTE_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("routes", routes);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++)  pages.add(i);
        request.setAttribute("pages", pages);
        return "search.jsp";
    }
}
