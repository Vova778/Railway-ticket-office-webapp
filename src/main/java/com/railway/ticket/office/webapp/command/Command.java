package com.railway.ticket.office.webapp.command;

import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute (HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException;
}
