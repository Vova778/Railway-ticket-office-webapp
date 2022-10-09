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

public class EditStationFormCommand implements Command {
    private static final Logger LOGGER
            = LogManager.getLogger(EditStationFormCommand.class);
    private final StationService stationService;

    public EditStationFormCommand(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        Station station = null;
        int id = Integer.parseInt(req.getParameter("stationId"));
        try {
            station = stationService.findStationById(id);
        } catch (ServiceException e) {
            LOGGER.error("[EditStationFormCommand] Can't receive station by id:[{}]", id);
            throw new CommandException(e.getMessage(), e);
        }
        req.setAttribute("station", station);
        return "edit_station.jsp";
    }
}
