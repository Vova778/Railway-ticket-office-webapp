package com.railway.ticket.office.webapp.command;

import com.railway.ticket.office.webapp.command.route.EditRouteFormCommand;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.service.StationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class HomeCommand implements Command {
    private static final Logger log
            = LogManager.getLogger(EditRouteFormCommand.class);
    private final StationService stationService;

    public HomeCommand(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException {
        HttpSession session = req.getSession();
        List<Station> stations;
        try {
            stations = stationService.findAll();
        } catch (ServiceException e) {
            log.error("[EditRouteFormCommand] Can't receive stations! " +
                    "An exception occurs: [{}]", e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        session.setAttribute("stations", stations);
        return "home.jsp";
    }
}
