package com.railway.ticket.office.webapp.command.schedule;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateScheduleFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException, FatalApplicationException {
        return "create_schedule.jsp";
    }
}
