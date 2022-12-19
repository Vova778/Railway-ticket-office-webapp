package com.railway.ticket.office.webapp.command.schedule;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.Schedule;
import com.railway.ticket.office.webapp.model.Train;
import com.railway.ticket.office.webapp.service.ScheduleService;
import com.railway.ticket.office.webapp.service.TrainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CreateScheduleCommand implements Command {
    private static final Logger LOGGER
            = LogManager.getLogger(CreateScheduleCommand.class);
    private static final String CREATE_SCHEDULE_COMMAND = "[CreateScheduleCommand]";

    private final ScheduleService scheduleService;
    private final TrainService trainService;

    public CreateScheduleCommand(ScheduleService scheduleService, TrainService trainService) {
        this.scheduleService = scheduleService;
        this.trainService = trainService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        Schedule schedule;
        try {
            Date date = Date.valueOf(req.getParameter("date"));

            Train train = trainService.findTrainById(
                    Integer.parseInt(req.getParameter("trainNumber")));

            schedule = new Schedule();

            schedule.setDate(date);
            schedule.setTrain(train);

            LOGGER.info("{} Schedule from view : {};",
                    CREATE_SCHEDULE_COMMAND,schedule);

            scheduleService.insert(schedule);
            LOGGER.info("{} Schedule was successfully saved : {}",
                    CREATE_SCHEDULE_COMMAND, schedule);
        } catch (ServiceException e) {
            LOGGER.error("An exception occurs while saving schedule");
            throw new CommandException(e.getMessage(), e);
        }
        return "controller?command=schedule&scheduleId="+schedule.getId();
    }
}