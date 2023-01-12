package com.railway.ticket.office.webapp.command.user;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AllUsersCommand implements Command {
    private static final Logger log = LogManager.getLogger(AllUsersCommand.class);
    private static final String ALL_USERS_COMMAND = "[AllUsersCommand]";
    private final UserService userService;

    public AllUsersCommand(UserService userService) {
        this.userService = userService;

    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws CommandException, FatalApplicationException {
        int page;
        List<User> users;
        int countPages;
        if (request.getParameter("page") == null
                || request.getParameter("page").equals("")) {
            page = 1;
        } else {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
            users = userService.findAll((page-1)*10);
            log.info("{} User found.", ALL_USERS_COMMAND);
            countPages = userService.countRecords() / 10 + 1;
        } catch (ServiceException e) {
            log.error("{} Can't receive users! " +
                    "An exception occurs: [{}]", ALL_USERS_COMMAND, e.getMessage());
            throw new CommandException(e.getMessage(), e);
        }
        request.setAttribute("users", users);

        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i <= countPages; i++)  pages.add(i);
        request.setAttribute("pages", pages);
        return "users.jsp";
    }
}
