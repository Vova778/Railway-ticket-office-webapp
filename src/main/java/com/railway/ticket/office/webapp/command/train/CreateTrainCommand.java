package com.railway.ticket.office.webapp.command.train;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.command.station.CreateStationCommand;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.TrainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateTrainCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateStationCommand.class);
    private static final String ADD_TRAIN_COMMAND = "[AddTrainCommand]";

    private final TrainService trainService;

    public CreateTrainCommand(TrainService trainService) {
        this.trainService = trainService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        Train train;
        try {
            int number = Integer.parseInt(req.getParameter("number"));
            int seats = Integer.parseInt(req.getParameter("seats"));

            train = new Train(number, seats);
            log.info("{} Train from view : {};", ADD_TRAIN_COMMAND,train);

            trainService.insert(train);
            log.info("{} Train was successfully saved : {}", ADD_TRAIN_COMMAND, train);
        } catch (ServiceException e) {
            log.error("An exception occurs while saving train");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=trains";
    }
}
