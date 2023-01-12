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
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private static final String LOGIN_COMMAND = "[LoginCommand]";
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, FatalApplicationException {
        String page = null;
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user;
        try {
            user = userService.findByLogin(login);
            if (user.getLogin() == null) {
                log.info("Cannot Login");
                page = "controller?command=registration_form";
                return page;
            }
            if (PasswordEncryption.validate(PasswordEncryption.getEncrypted(password)
                    , String.valueOf(user.getPassword()))) {
                log.info("{} User received from db: {}",
                        LOGIN_COMMAND, user.getLogin());
                session.setAttribute("user", user);
                if (user.getRole().getId() == User.Role.ADMIN.getId()) {
                    page = "controller?command=home";
                }
                if (user.getRole().getId() == User.Role.USER.getId()) {
                    page = "controller?command=home";
                }
            }
            return page;
        } catch (ServiceException e) {
            log.error("{} An exception occurs : {}", LOGIN_COMMAND, e.getMessage());
            throw new FatalApplicationException(e.getMessage(), e);
        }
    }
}
