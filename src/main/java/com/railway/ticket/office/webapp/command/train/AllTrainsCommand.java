package com.railway.ticket.office.webapp.command.train;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.TrainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AllTrainsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AllTrainsCommand.class);
    private static final String ALL_TRAINS_COMMAND = "[AllTrainsCommand]";
    private final TrainService trainService;

    public AllTrainsCommand(TrainService trainService) {
        this.trainService = trainService;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException, FatalApplicationException {
        int page;
        List<Train> trains = null;
        int countPages;
        if (request.getParameter("page") == null
                || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
            trains = trainService.findAll((page-1)*10);
            LOGGER.info("{} Train found.", ALL_TRAINS_COMMAND);
            countPages = trainService.countRecords() / 10 + 1;
        } catch (ServiceException e) {
            LOGGER.error("{} Can't receive trains! " +
                    "An exception occurs: [{}]", ALL_TRAINS_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("trains", trains);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++)  pages.add(i);
        request.setAttribute("pages", pages);
        return "trains.jsp";
    }
}
