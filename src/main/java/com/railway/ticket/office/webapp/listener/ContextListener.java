package com.railway.ticket.office.webapp.listener;

import com.railway.ticket.office.webapp.command.*;
import com.railway.ticket.office.webapp.command.route.AllRoutesCommand;
import com.railway.ticket.office.webapp.command.station.AllStationsCommand;
import com.railway.ticket.office.webapp.command.train.AllTrainsCommand;
import com.railway.ticket.office.webapp.command.user.*;
import com.railway.ticket.office.webapp.db.dao.*;
import com.railway.ticket.office.webapp.db.dao.factory.DAOFactory;
import com.railway.ticket.office.webapp.service.*;
import com.railway.ticket.office.webapp.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements HttpSessionListener, ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);
    private static final String CONTEXT_LISTENER_MSG = "[ContextListener]";


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // empty
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // empty
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("[ContextListener] Context initialization started...");
        ServletContext context = sce.getServletContext();
        context.setInitParameter("encoding", "UTF-8");
        try {
            initServices(context);
            LOGGER.info("{} Services initialized", CONTEXT_LISTENER_MSG);
        } catch (SQLException e) {
            LOGGER.error("{} Services initialization failed!..", CONTEXT_LISTENER_MSG);
            throw new RuntimeException(e);
        }
    }

    private void initServices(ServletContext context) throws SQLException {
      /*  Connection connection = ConnectionPoolHolder.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        LOGGER.info("{} Connection created. {}", CONTEXT_LISTENER_MSG, connection.getMetaData());
*/      DAOFactory daoFactory =DAOFactory.getInstance();

        UserDAO userDAO = daoFactory.getUserDAO();
        LOGGER.info("{} UserDAO created.", CONTEXT_LISTENER_MSG);

        TrainDAO trainDAO = daoFactory.getTrainDAO();
        LOGGER.info("{} TrainDAO created.", CONTEXT_LISTENER_MSG);

        StationDAO stationDAO = daoFactory.getStationDAO();
        LOGGER.info("{} StationDAO created.", CONTEXT_LISTENER_MSG);

        ScheduleDAO scheduleDAO = daoFactory.getScheduleDAO();
        LOGGER.info("{} ScheduleDAO created.", CONTEXT_LISTENER_MSG);

        RouteDAO routeDAO = daoFactory.getRouteDAO();
        LOGGER.info("{} RouteDAO created.", CONTEXT_LISTENER_MSG);

        TicketDAO ticketDAO = daoFactory.getTicketDAO();
        LOGGER.info("{} TicketDAO created.", CONTEXT_LISTENER_MSG);

        UserService userService = new UserServiceImpl(userDAO);
        context.setAttribute("userService", userService);
        LOGGER.info("{} UserService created.", CONTEXT_LISTENER_MSG);

        TrainService trainService = new TrainServiceImpl(trainDAO);
        context.setAttribute("trainService", trainService);
        LOGGER.info("{}  TrainService created.", CONTEXT_LISTENER_MSG);

        StationService stationService = new StationServiceImpl(stationDAO);
        context.setAttribute("stationService", stationService);
        LOGGER.info("{} StationService created.", CONTEXT_LISTENER_MSG);

        ScheduleService scheduleService = new ScheduleServiceImpl(scheduleDAO);
        context.setAttribute("scheduleService", scheduleService);
        LOGGER.info("{} ScheduleService created.", CONTEXT_LISTENER_MSG);

        RouteService routeService = new RouteServiceImpl(routeDAO);
        context.setAttribute("routeService", routeService);
        LOGGER.info("{} RouteService created.", CONTEXT_LISTENER_MSG);

        TicketService ticketService = new TicketServiceImpl(ticketDAO);
        context.setAttribute("ticketService", ticketService);
        LOGGER.info("{} TicketService created.", CONTEXT_LISTENER_MSG);

        CommandContainer commandContainer = new CommandContainer();
        LOGGER.info("{} CommandContainer created.", CONTEXT_LISTENER_MSG);

        Command appCommand = new HomeCommand();
        commandContainer.addCommand("home", appCommand);
        commandContainer.addCommand("", appCommand);
        commandContainer.addCommand(null, appCommand);

        appCommand = new LanguageCommand();
        commandContainer.addCommand("setLang", appCommand);
        LOGGER.info("{} Language command created.", CONTEXT_LISTENER_MSG);

        appCommand = new ExceptionCommand();
        commandContainer.addCommand("error", appCommand);
        LOGGER.info("{} Error command created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationFormCommand();
        commandContainer.addCommand("registrationForm", appCommand);
        LOGGER.info("{} RegistrationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationCommand(userService);
        commandContainer.addCommand("registration", appCommand);
        LOGGER.info("{} Registration command created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginFormCommand();
        commandContainer.addCommand("loginForm", appCommand);
        LOGGER.info("{} LoginFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginCommand(userService);
        commandContainer.addCommand("login", appCommand);
        LOGGER.info("{} LoginCommand created.", CONTEXT_LISTENER_MSG);

       /* appCommand = new MenuCommand(ticketService);
        commandContainer.addCommand("menu", appCommand);
        LOGGER.info("{} MenuCommand created.", CONTEXT_LISTENER_MSG);*/

        appCommand = new LogoutCommand();
        commandContainer.addCommand("logout", appCommand);
        LOGGER.info("{} LogoutCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllRoutesCommand(routeService);
        commandContainer.addCommand("routes", appCommand);
        LOGGER.info("{} AllRoutesCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllStationsCommand(stationService);
        commandContainer.addCommand("stations", appCommand);
        LOGGER.info("{} AllStationsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllUsersCommand(userService);
        commandContainer.addCommand("users", appCommand);
        LOGGER.info("{} AllUsersCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllTrainsCommand(trainService);
        commandContainer.addCommand("trains", appCommand);
        LOGGER.info("{} AllTrainsCommand created.", CONTEXT_LISTENER_MSG);

        context.setAttribute("commandContainer", commandContainer);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
