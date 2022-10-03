package com.railway.ticket.office.webapp.command;

import com.railway.ticket.office.webapp.exceptions.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LanguageCommand implements Command  {
    private static final Logger LOGGER = LogManager.getLogger(LanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute("locale", request.getParameter("locale"));
        LOGGER.info("[LanguageCommand] Page to process = {}",
                request.getParameter("pageToProcess"));

        return "controller?command=" + request.getParameter("pageToProcess");
    }
}
