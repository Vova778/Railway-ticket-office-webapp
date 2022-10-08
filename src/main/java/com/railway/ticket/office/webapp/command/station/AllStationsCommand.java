package com.railway.ticket.office.webapp.command.station;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AllStationsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AllStationsCommand.class);
    private static final String ALL_STATIONS_COMMAND = "[AllStationsCommand]";
    private final StationService stationService;

    public AllStationsCommand(StationService stationService) {
        this.stationService = stationService;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException, FatalApplicationException {
        int page;
        List<Station> stations = null;
        int countPages;
        if (request.getParameter("page") == null
                || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
            stations = stationService.findAll((page-1)*10);
            LOGGER.info("{} Stations found.", ALL_STATIONS_COMMAND);
            countPages = stationService.countRecords() / 10 + 1;
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive stations! " +
                    "An exception occurs: [{}]", ALL_STATIONS_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("stations", stations);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++)  pages.add(i);
        request.setAttribute("pages", pages);
        return "stations.jsp";
    }
}
