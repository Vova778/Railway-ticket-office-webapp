package com.railway.ticket.office.webapp.controller;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.command.CommandContainer;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    public void init(ServletConfig config){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(request, response);
            LOGGER.info("[Controller-doGet] URL processed");
        } catch (CommandException | FatalApplicationException e) {
            LOGGER.error("An exception occurs: {}, req encoding: {}", e.getMessage(), request.getCharacterEncoding());
            response.sendError(500, "Can't process command");
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = null;
        try {
            url = getUrl(request, response);
            LOGGER.info("[Controller-doPost] URL processed [{}], resp encoding: {}", url,
                    response.getCharacterEncoding());
        } catch (CommandException | FatalApplicationException e) {
            LOGGER.error("An exception occurs: {}", e.getMessage());
            response.sendError(500, "Can't process the command");
        }
        response.sendRedirect(url);
    }

    public String getUrl(HttpServletRequest req, HttpServletResponse resp)
            throws CommandException, FatalApplicationException {
        String commandName = req.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        return command.execute(req, resp);
    }
}
