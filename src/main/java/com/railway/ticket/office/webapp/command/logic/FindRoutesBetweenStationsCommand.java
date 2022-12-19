package com.railway.ticket.office.webapp.command.logic;

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
import com.railway.ticket.office.webapp.service.TicketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FindRoutesBetweenStationsCommand implements Command {
    private static final Logger LOGGER  = LogManager.getLogger(FindRoutesBetweenStationsCommand.class);
    private static final String FIND_ROUTE_COMMAND = "[FindRouteCommand]";

    private final StationService stationService;
    private final RouteService routeService;

    public FindRoutesBetweenStationsCommand(StationService stationService, ScheduleService scheduleService, RouteService routeService, TicketService ticketService) {
        this.stationService = stationService;
        this.routeService = routeService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse resp) throws CommandException, FatalApplicationException {
        List<Route> routes = new ArrayList<>();
        HttpSession session = request.getSession();
        List<Schedule> schedules = null;
        int countPages;

        try {
            Date date = Date.valueOf(request.getParameter("date"));

            Station startingStation = stationService.findStationByName(
                    request.getParameter("startingStation")).get();

            Station finalStation = stationService.findStationByName(
                    request.getParameter("finalStation")).get();



            schedules = routeService
                    .findRoutesBetweenStations(startingStation,finalStation)
                    .stream()
                    .collect(
                            Collectors.groupingBy(Route::getSchedule))
                    .entrySet()
                    .stream()
                    .map(scheduleListEntry -> {
                        scheduleListEntry.getKey().setRoutes(scheduleListEntry.getValue());
                        return scheduleListEntry.getKey();
                    }).collect(Collectors.toList());

            int id =0;

            for (Schedule schedule: schedules) {

                Route route = new Route();
                route.setStartingStation(startingStation);
                route.setFinalStation(finalStation);

                route.setDepartureTime(schedule.getRoutes().get(0).getDepartureTime());
                route.setTrain(schedule.getRoutes().get(0).getTrain());
                route.setSchedule(schedule.getRoutes().get(0).getSchedule());
                route.setArrivalTime(
                        schedule.getRoutes()
                                .get(schedule.getRoutes().size()-1)
                                .getArrivalTime());


                double price = schedule.getRoutes()
                        .stream()
                        .mapToDouble(Route::getPrice)
                        .sum();
                int availableSeats = schedule.getRoutes()
                        .stream()
                        .mapToInt(Route::getAvailableSeats)
                        .min().getAsInt();

                route.setId(id++);
                route.setTravelTime(new Time(route.getArrivalTime().getTime()
                        -route.getDepartureTime().getTime() - 7200000 ));
                route.setAvailableSeats(availableSeats);
                route.setPrice(price);
                routes.add(route);

            }
            session.setAttribute("routes", routes);
            LOGGER.info("{} Routes between stations were found.", FIND_ROUTE_COMMAND);
            countPages = schedules.size() / 10 + 1;
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive routes! " +
                    "An exception occurs: [{}]", FIND_ROUTE_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("routes", routes);
        request.setAttribute("schedules", schedules);


        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++)  pages.add(i);
        request.setAttribute("pages", pages);
        return "search.jsp";
    }
    private static Timestamp toTimeStamp(Date date, Time time, int day){
        return new Timestamp(date.getTime()+time.getTime()+86400000*day);
    }

}
