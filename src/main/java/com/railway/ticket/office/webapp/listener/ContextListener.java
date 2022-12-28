package com.railway.ticket.office.webapp.listener;

import com.railway.ticket.office.webapp.command.*;
import com.railway.ticket.office.webapp.command.route.*;
import com.railway.ticket.office.webapp.command.schedule.CreateScheduleCommand;
import com.railway.ticket.office.webapp.command.schedule.CreateScheduleFormCommand;
import com.railway.ticket.office.webapp.command.schedule.RemoveScheduleCommand;
import com.railway.ticket.office.webapp.command.schedule.ScheduleCommand;
import com.railway.ticket.office.webapp.command.station.*;
import com.railway.ticket.office.webapp.command.ticket.BookTicketCommand;
import com.railway.ticket.office.webapp.command.ticket.CancelTicketCommand;
import com.railway.ticket.office.webapp.command.ticket.TicketDetailsCommand;
import com.railway.ticket.office.webapp.command.train.AllTrainsCommand;
import com.railway.ticket.office.webapp.command.train.CreateTrainCommand;
import com.railway.ticket.office.webapp.command.train.CreateTrainFormCommand;
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
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements HttpSessionListener, ServletContextListener {
    private static final Logger log = LogManager.getLogger(ContextListener.class);
    private static final String CONTEXT_LISTENER_MSG = "[ContextListener]";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("[ContextListener] Context initialization started...");
        ServletContext context = sce.getServletContext();
        context.setInitParameter("encoding", "UTF-8");
        try {
            initServices(context);
            log.info("{} Services initialized", CONTEXT_LISTENER_MSG);
        } catch (SQLException e) {
            log.error("{} Services initialization failed!..", CONTEXT_LISTENER_MSG);
            throw new RuntimeException(e);
        }
    }

    private void initServices(ServletContext context) throws SQLException {

        DAOFactory daoFactory =DAOFactory.getInstance();

        UserDAO userDAO = daoFactory.getUserDAO();
        log.info("{} UserDAO created.", CONTEXT_LISTENER_MSG);

        TrainDAO trainDAO = daoFactory.getTrainDAO();
        log.info("{} TrainDAO created.", CONTEXT_LISTENER_MSG);

        StationDAO stationDAO = daoFactory.getStationDAO();
        log.info("{} StationDAO created.", CONTEXT_LISTENER_MSG);

        ScheduleDAO scheduleDAO = daoFactory.getScheduleDAO();
        log.info("{} ScheduleDAO created.", CONTEXT_LISTENER_MSG);

        RouteDAO routeDAO = daoFactory.getRouteDAO();
        log.info("{} RouteDAO created.", CONTEXT_LISTENER_MSG);

        TicketDAO ticketDAO = daoFactory.getTicketDAO();
        log.info("{} TicketDAO created.", CONTEXT_LISTENER_MSG);

        UserService userService = new UserServiceImpl(userDAO);
        context.setAttribute("userService", userService);
        log.info("{} UserService created.", CONTEXT_LISTENER_MSG);

        TrainService trainService = new TrainServiceImpl(trainDAO);
        context.setAttribute("trainService", trainService);
        log.info("{}  TrainService created.", CONTEXT_LISTENER_MSG);

        StationService stationService = new StationServiceImpl(stationDAO);
        context.setAttribute("stationService", stationService);
        log.info("{} StationService created.", CONTEXT_LISTENER_MSG);

        ScheduleService scheduleService = new ScheduleServiceImpl(scheduleDAO);
        context.setAttribute("scheduleService", scheduleService);
        log.info("{} ScheduleService created.", CONTEXT_LISTENER_MSG);

        RouteService routeService = new RouteServiceImpl(routeDAO);
        context.setAttribute("routeService", routeService);
        log.info("{} RouteService created.", CONTEXT_LISTENER_MSG);

        TicketService ticketService = new TicketServiceImpl(ticketDAO);
        context.setAttribute("ticketService", ticketService);
        log.info("{} TicketService created.", CONTEXT_LISTENER_MSG);

        CommandContainer commandContainer = new CommandContainer();
        log.info("{} CommandContainer created.", CONTEXT_LISTENER_MSG);

        Command appCommand = new HomeCommand();
        commandContainer.addCommand("home", appCommand);
        commandContainer.addCommand("", appCommand);
        commandContainer.addCommand(null, appCommand);

        appCommand = new LanguageCommand();
        commandContainer.addCommand("setLang", appCommand);
        log.info("{} Language command created.", CONTEXT_LISTENER_MSG);

        appCommand = new ExceptionCommand();
        commandContainer.addCommand("error", appCommand);
        log.info("{} Error command created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationFormCommand();
        commandContainer.addCommand("registrationForm", appCommand);
        log.info("{} RegistrationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RegistrationCommand(userService);
        commandContainer.addCommand("registration", appCommand);
        log.info("{} Registration command created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginFormCommand();
        commandContainer.addCommand("loginForm", appCommand);
        log.info("{} LoginFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LoginCommand(userService);
        commandContainer.addCommand("login", appCommand);
        log.info("{} LoginCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new LogoutCommand();
        commandContainer.addCommand("logout", appCommand);
        log.info("{} LogoutCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllRoutesCommand(routeService);
        commandContainer.addCommand("routes", appCommand);
        log.info("{} AllRoutesCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllStationsCommand(stationService);
        commandContainer.addCommand("stations", appCommand);
        log.info("{} AllStationsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllUsersCommand(userService);
        commandContainer.addCommand("users", appCommand);
        log.info("{} AllUsersCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AllTrainsCommand(trainService);
        commandContainer.addCommand("trains", appCommand);
        log.info("{} AllTrainsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new ScheduleCommand(routeService, stationService);
        commandContainer.addCommand("schedule", appCommand);
        log.info("{} ScheduleCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateStationFormCommand();
        commandContainer.addCommand("station_form", appCommand);
        log.info("{} StationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateStationCommand(stationService);
        commandContainer.addCommand("create_station", appCommand);
        log.info("{} CreateStationCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditStationCommand(stationService);
        commandContainer.addCommand("edit_station", appCommand);
        log.info("{} EditStationCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditStationFormCommand(stationService);
        commandContainer.addCommand("edit_station_form", appCommand);
        log.info("{} EditStationFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new AddRouteFormCommand(stationService, routeService);
        commandContainer.addCommand("add_route_form", appCommand);
        log.info("{} RouteFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateRouteCommand(routeService ,stationService, scheduleService);
        commandContainer.addCommand("add_route", appCommand);
        log.info("{} AddRouteCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RemoveRouteCommand(routeService);
        commandContainer.addCommand("remove_route", appCommand);
        log.info("{} RemoveRouteCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditRouteFormCommand(routeService, stationService);
        commandContainer.addCommand("edit_route_form", appCommand);
        log.info("{} EditRouteFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new EditRouteCommand(routeService,stationService);
        commandContainer.addCommand("edit_route", appCommand);
        log.info("{} EditRouteCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new FindRoutesBetweenStationsCommand(stationService, routeService);
        commandContainer.addCommand("find_routes_between_stations", appCommand);
        log.info("{} FindRoutesBetweenStationsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new BookTicketCommand(ticketService);
        commandContainer.addCommand("book_ticket", appCommand);
        log.info("{} BookTicketCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new UserTicketsCommand(ticketService);
        commandContainer.addCommand("basket", appCommand);
        log.info("{} UserTicketsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CancelTicketCommand(ticketService);
        commandContainer.addCommand("cancel_ticket", appCommand);
        log.info("{} CancelTicketCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new TicketDetailsCommand(routeService);
        commandContainer.addCommand("ticket_details", appCommand);
        log.info("{} TicketDetailsCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateTrainCommand(trainService);
        commandContainer.addCommand("create_train", appCommand);
        log.info("{} AddTrainCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateTrainFormCommand();
        commandContainer.addCommand("train_form", appCommand);
        log.info("{} AddTrainFormCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateScheduleCommand(scheduleService, trainService);
        commandContainer.addCommand("create_schedule", appCommand);
        log.info("{} CreateScheduleCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new RemoveScheduleCommand(scheduleService);
        commandContainer.addCommand("remove_schedule", appCommand);
        log.info("{} RemoveScheduleCommand created.", CONTEXT_LISTENER_MSG);

        appCommand = new CreateScheduleFormCommand();
        commandContainer.addCommand("create_schedule_form", appCommand);
        log.info("{} CreateScheduleFormCommand created.", CONTEXT_LISTENER_MSG);


        context.setAttribute("commandContainer", commandContainer);

    }

}
