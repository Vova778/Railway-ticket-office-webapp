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

public class EditStationCommand implements Command {
    private static final Logger LOGGER =
            LogManager.getLogger(EditStationCommand.class);
    private final StationService stationService;

    public EditStationCommand(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest req,
                          HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        String name = req.getParameter("name");
        try {
            Station toUpdate = stationService
                    .findById(Integer.parseInt(req.getParameter("stationId")));

            if (name != null && !name.isEmpty()) {
                toUpdate.setName(name);
            }

            stationService.update(toUpdate);

            return "controller?command=stations";
        } catch (ServiceException e) {
            LOGGER.error("[EditStationCommand] Can't update station");
            throw new CommandException(e.getMessage(), e);
        }
    }
}
