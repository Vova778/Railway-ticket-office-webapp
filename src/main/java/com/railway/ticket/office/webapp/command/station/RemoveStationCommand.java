package com.railway.ticket.office.webapp.command.station;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveStationCommand implements Command {
    private static final Logger log = LogManager.getLogger(RemoveStationCommand.class);
    private static final String REMOVE_SCHEDULE_COMMAND = "[RemoveStationCommand]";
    private final StationService stationService;

    public RemoveStationCommand(StationService stationService) {
        this.stationService = stationService;
    }


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        try {
            stationService.delete(Integer.parseInt(request.getParameter("stationId")));

            return "controller?command=stations";
        } catch (ServiceException e) {
            log.error("{} Station wasn't removed. An exception occurs: [{}]",
                    REMOVE_SCHEDULE_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
    }
}
