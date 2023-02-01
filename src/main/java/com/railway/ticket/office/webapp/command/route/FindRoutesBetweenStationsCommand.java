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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Map<Route, List<Route>> routes;
        HttpSession session = request.getSession();
        int countPages;
        int page;

        if (request.getParameter("page") == null
                || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            Date date = Date.valueOf(request.getParameter("date"));

            Station startingStation = stationService.findByName(
                    request.getParameter("startingStation"));

            Station finalStation = stationService.findByName(
                    request.getParameter("finalStation"));

            routes = routeService
                    .findRoutesBetweenStations(date, finalStation, startingStation)
                    .stream()
                    .collect(Collectors.groupingBy(Route::getSchedule))
                    .entrySet()
                    .stream()
                    .skip((page-1)*10)
                    .collect(
                            Collectors.toMap(
                                    e -> toGeneralRoute(startingStation, finalStation, e)
                                    , Map.Entry::getValue));


            session.setAttribute("routes", routes);
            log.info("{} Routes between stations were found.", FIND_ROUTE_COMMAND);
            countPages = routes.size() / 10 + 1;
        } catch (ServiceException e) {
            log.error("{} Can't receive routes! " +
                    "An exception occurs: [{}]", FIND_ROUTE_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("routes", routes);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) pages.add(i);
        request.setAttribute("pages", pages);
        return "search.jsp";
    }


    /**
     * Generalizes routes to a common object
     *
     */
    private Route toGeneralRoute(Station startingStation,
                                 Station finalStation,
                                 Map.Entry<Schedule, List<Route>> e) {
        Route route = new Route();
        route.setStartingStation(startingStation);
        route.setFinalStation(finalStation);

        route.setDepartureTime(e.getValue().get(0).getDepartureTime());
        route.setTrain(e.getValue().get(0).getTrain());
        route.setSchedule(e.getValue().get(0).getSchedule());
        route.setArrivalTime(
                e.getValue()
                        .get(e.getValue().size() - 1)
                        .getArrivalTime());


        double price = e.getValue()
                .stream()
                .mapToDouble(Route::getPrice)
                .sum();
        int availableSeats = e.getValue()
                .stream()
                .mapToInt(Route::getAvailableSeats)
                .min()
                .getAsInt();

        route.setTravelTime(new Time(route.getArrivalTime().getTime()
                - route.getDepartureTime().getTime() - 7200000));
        route.setAvailableSeats(availableSeats);
        route.setPrice(price);
        return route;
    }
}
