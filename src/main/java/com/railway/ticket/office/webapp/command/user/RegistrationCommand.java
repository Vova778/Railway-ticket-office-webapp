package com.railway.ticket.office.webapp.command.user;

import com.railway.ticket.office.webapp.command.Command;
import com.railway.ticket.office.webapp.exceptions.CommandException;
import com.railway.ticket.office.webapp.exceptions.FatalApplicationException;
import com.railway.ticket.office.webapp.exceptions.ServiceException;
import com.railway.ticket.office.webapp.model.User;
import com.railway.ticket.office.webapp.service.UserService;
import com.railway.ticket.office.webapp.utils.security.PasswordEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegistrationCommand.class);
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException, FatalApplicationException {
        User user = null;
        try {
            user = User.newBuilder()
                    .setLogin(req.getParameter("login"))
                    .setFirstName(req.getParameter("firstName"))
                    .setLastName(req.getParameter("lastName"))
                    .setPhone(req.getParameter("phone"))
                    .setPassword(PasswordEncryption.getEncrypted(req.getParameter("password")))
                    .setRole(User.Role.USER)
                    .build();
            log.info("[RegistrationCommand] User from view : {} + fN: {}", user, req.getParameter("firstName"));
            if (userService.isUserExists(user)) {
                return "controller?command=login_form";
            }
            userService.insert(user);
            log.info("[RegistrationCommand] User saved : {}", user);
        } catch (ServiceException e) {
            log.error("An exception occurs while saving User");
            throw new CommandException(e.getMessage(), e);
        }
        return "login.jsp";
    }
}