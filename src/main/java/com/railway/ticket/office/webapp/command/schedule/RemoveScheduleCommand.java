package com.railway.ticket.office.webapp.command.schedule;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.service.ScheduleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RemoveScheduleCommand implements Command {

    private static final Logger log = LogManager.getLogger(RemoveScheduleCommand.class);
    private static final String REMOVE_SCHEDULE_COMMAND = "[RemoveScheduleCommand]";
    private final ScheduleService scheduleService;

    public RemoveScheduleCommand(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws CommandException {
        try {
            scheduleService.delete(Integer.parseInt(request.getParameter("scheduleId")));

            return "controller?command=trains";
        } catch (ServiceException e) {
            log.error("{} Schedule wasn't removed. An exception occurs: [{}]",
                    REMOVE_SCHEDULE_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
    }
}
