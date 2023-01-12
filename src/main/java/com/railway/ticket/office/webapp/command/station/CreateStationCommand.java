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

public class CreateStationCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateStationCommand.class);
    private static final String CREATE_STATION_COMMAND = "[CreateStationCommand]";

    private final StationService stationService;

    public CreateStationCommand(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        Station station;
        try {
            String name = req.getParameter("stationName");
            station = new Station();
            station.setName(name);
            log.info("{} Station from view : {};",CREATE_STATION_COMMAND ,station);

            stationService.insert(station);
            log.info("{} Station was successfully saved : {}",CREATE_STATION_COMMAND, station);
        } catch (ServiceException e) {
            log.error("An exception occurs while saving station");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=stations";
    }
}
