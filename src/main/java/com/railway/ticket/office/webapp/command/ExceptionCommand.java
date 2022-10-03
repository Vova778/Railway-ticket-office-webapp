package com.railway.ticket.office.webapp.command;

import com.railway.ticket.office.webapp.exceptions.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException {
        return "errorPage.jsp";
    }
}
