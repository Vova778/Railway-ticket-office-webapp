package com.railway.ticket.office.webapp.db.dao.impl;

import com.railway.ticket.office.webapp.db.Constants;
import com.railway.ticket.office.webapp.db.dao.RouteDAO;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.RouteMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Station;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Class implements functionality of manipulations with
 * {@link Route} entity using MySQL database.
 * Constructor param :
 * @see java.sql.Connection
 */

public class RouteDAOImpl implements RouteDAO {

    private final Connection con;
    private static final Logger log = LogManager.getLogger(RouteDAOImpl.class);
    private final RouteMapper routeMapper = new RouteMapper();

    public RouteDAOImpl(Connection connection){
        this.con=connection;
    }

    @Override
    public Connection getConnection() {
        return con;
    }

    @Override
    public int insert(Route route) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.ROUTES_INSERT_ROUTE,
                            Statement.RETURN_GENERATED_KEYS)) {

            setRouteParameters(route, preparedStatement);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            int key = 0;
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }

            log.info("Route : {} was inserted successfully", route);

            return key;

        } catch (SQLException e) {
            log.error("Route : [{}] was not inserted. An exception occurs : {}",
                    route, e.getMessage());
            throw new DAOException("[RouteDAO] exception while creating Route" + e.getMessage(), e);
        }
    }



    @Override
    public void delete(int routeId) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.ROUTES_DELETE_ROUTE)) {
            preparedStatement.setInt(1,routeId);
            int removedRow = preparedStatement.executeUpdate();

            if(removedRow>0){
                log.info("Route with ID : {} was removed successfully", routeId);
            }

        } catch (SQLException e) {
            log.error("Route with ID : [{}] was not removed. An exception occurs : {}",
                    routeId, e.getMessage());
            throw new DAOException("[RouteDAO] exception while removing Route" + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Route route) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.ROUTES_UPDATE_ROUTE)) {


            setRouteParameters(route, preparedStatement);

            preparedStatement.setInt(11,route.getId());

            int updatedRow = preparedStatement.executeUpdate();
            if(updatedRow>0){
                log.info("Route with ID : {} was updated successfully", route.getId());
                return true;
            } else {
                log.info("Route with ID : [{}] was not found for update", route.getId());
                return false;
            }

        } catch (SQLException e) {
            log.error("Route with ID : [{}] was not updated. An exception occurs : {}",
                    route.getId(), e.getMessage());
            throw new DAOException("[RouteDAO] exception while updating Route" + e.getMessage(), e);
        }
    }


    private void setRouteParameters(Route route, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;

        preparedStatement.setInt(k++, route.getStoppageNumber());
        preparedStatement.setInt(k++,route.getStartingStation().getId());
        preparedStatement.setInt(k++,route.getFinalStation().getId());
        preparedStatement.setTime(k++,route.getDepartureTime());
        preparedStatement.setTime(k++,route.getArrivalTime());
        preparedStatement.setInt(k++,route.getAvailableSeats());
        preparedStatement.setInt(k++,route.getDay());
        preparedStatement.setInt(k++,route.getSchedule().getId());
        preparedStatement.setInt(k++,route.getTrain().getNumber());
        preparedStatement.setDouble(k,route.getPrice());
    }

    @Override
    public Route findById(int routeId) throws DAOException {

        Optional<Route> route = Optional.empty();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.ROUTES_GET_ROUTE_BY_ID)) {

            preparedStatement.setInt(1,routeId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    route = Optional.ofNullable(new RouteMapper()
                            .extractFromResultSet(resultSet));
                }
            }
            route.ifPresent(r -> log.info("Route was received : [{}]",
                    r.getId()));

            return route.orElse(new Route());

        }
        catch (SQLException e) {
            log.error("Route with ID : [{}] was not found. An exception occurs : {}",
                    routeId, e.getMessage());
            throw new DAOException("[RouteDAO] exception while loading Route" + e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findRoutesByScheduleId(int scheduleId) throws DAOException {
        List<Route> routes = new ArrayList<>();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.ROUTES_GET_ROUTE_BY_SCHEDULE_ID)){

            preparedStatement.setInt(1, scheduleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    routes.add(routeMapper
                            .extractFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            log.error("Route with Schedule ID : [{}] was not found. An exception occurs : {}",
                    scheduleId, e.getMessage());
            throw new DAOException("[RouteDAO] exception while loading Route by Schedule ID" + e.getMessage(), e);
        }

        return routes;
    }

    @Override
    public List<Route> findRoutesByTicketId(int ticketId) throws DAOException {
        List<Route> routes = new ArrayList<>();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.ROUTES_GET_ROUTE_BY_TICKET_ID)){

            preparedStatement.setInt(1, ticketId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    routes.add(routeMapper
                            .extractFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            log.error("Route with Ticket ID : [{}] was not found. An exception occurs : {}",
                    ticketId, e.getMessage());
            throw new DAOException("[RouteDAO] exception while loading Route by Ticket ID" + e.getMessage(), e);
        }

        return routes;
    }


    @Override
    public List<Route> findAll() throws DAOException {
        List<Route> routes = new ArrayList<>();

        try(Statement statement = con.createStatement();
            ResultSet resultSet =
                    statement.executeQuery(Constants.ROUTES_GET_ALL_ROUTES)
        ) {

            RouteMapper routeMapper = new RouteMapper();
            while (resultSet.next()){
                routes.add(routeMapper.extractFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            log.error("Routes were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[RouteDAO] exception while reading all routes" + e.getMessage(), e);
        }

        return routes;
    }

    @Override
    public List<Route> findAll(int offset) throws DAOException {
        List<Route> routes = new ArrayList<>();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.ROUTES_GET_ALL_ROUTES_WITH_OFFSET)) {

            preparedStatement.setInt(1, offset);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    routes.add(routeMapper
                            .extractFromResultSet(resultSet));
                }
            }
            return routes;
        }
        catch (SQLException e) {
            log.error("Routes were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[RouteDAO] exception while reading all routes" + e.getMessage(), e);
        }
    }

    @Override
    public List<Route> findRoutesBetweenStations(Date date,
                                                 Station endStation,
                                                 Station startStation) throws DAOException {
        List<Route> routes = new ArrayList<>();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.ROUTES_FIND_ROUTES_BETWEEN_STATIONS)) {

            int k =1;
            preparedStatement.setInt(k++, startStation.getId());
            preparedStatement.setDate(k++, date);
            preparedStatement.setInt(k++, startStation.getId());
            preparedStatement.setInt(k, endStation.getId());


            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    routes.add(routeMapper
                            .extractFromResultSet(resultSet));
                }
            }
            return routes;
        }
        catch (SQLException e) {
            log.error("Routes were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[RouteDAO] exception while reading all routes" + e.getMessage(), e);
        }
    }

    /**
     * @return all {@link Route} records in database
     */
    @Override
    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement = con.prepareStatement(Constants.ROUTES_GET_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            log.error("[RouteDAO] Failed to count routes! An exception occurs :[{}]",
                    e.getMessage());
        }
        return recordsCount;
    }
}
