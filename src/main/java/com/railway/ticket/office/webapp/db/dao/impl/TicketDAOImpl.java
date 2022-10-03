package com.railway.ticket.office.webapp.db.dao.impl;

import com.railway.ticket.office.webapp.db.Constants;
import com.railway.ticket.office.webapp.db.dao.TicketDAO;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.TicketMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDAOImpl implements TicketDAO {

    private final Connection con;
    private static final Logger LOGGER = LogManager.getLogger(TicketDAOImpl.class);
    private final TicketMapper ticketMapper = new TicketMapper();

    public TicketDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Connection getConnection() {
        return con;
    }
    @Override
    public void insertTicket(Ticket ticket) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.TICKETS_INSERT_TICKET,
                            Statement.RETURN_GENERATED_KEYS)) {

            con.setAutoCommit(false);

            setTicketParameters(ticket, preparedStatement);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                ticket.setId(resultSet.getInt(1));
                LOGGER.info("Ticket : {} was inserted successfully", ticket);
            }


        } catch (SQLException e) {
            LOGGER.error("Ticket : [{}] was not inserted. An exception occurs : {}",
                    ticket, e.getMessage());
            throw new DAOException("[TicketDAO] exception while creating Ticket" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTicket(int ticketId) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.TICKETS_DELETE_TICKET)) {

            preparedStatement.setInt(1,ticketId);

            int removedRow = preparedStatement.executeUpdate();

            if(removedRow>0){
                LOGGER.info("Ticket with ID : {} was removed successfully", ticketId);
            }

        } catch (SQLException e) {
            LOGGER.error("Ticket with ID : [{}] was not removed. An exception occurs : {}",
                    ticketId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while removing Ticket" + e.getMessage(), e);
        }
    }

    @Override
    public void updateTicket(int ticketId, Ticket ticket) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.TICKETS_UPDATE_TICKET)) {

            setTicketParameters(ticket, preparedStatement);

            preparedStatement.setInt(9,ticketId);


            int updatedRow = preparedStatement.executeUpdate();
            if(updatedRow>0){
                LOGGER.info("Ticket with ID : {} was updated successfully", ticketId);
            }

        } catch (SQLException e) {
            LOGGER.error("Ticket with ID : [{}] was not updated. An exception occurs : {}",
                    ticketId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while updating Ticket" + e.getMessage(), e);
        }
    }

    private void setTicketParameters(Ticket ticket, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setDouble(k++,ticket.getFare());
        preparedStatement.setInt(k++,ticket.getStartingStationId());
        preparedStatement.setInt(k++,ticket.getFinalStationId());
        preparedStatement.setTimestamp(k++,ticket.getDepartureTime());
        preparedStatement.setTimestamp(k++,ticket.getArrivalTime());
        preparedStatement.setInt(k++,ticket.getTrainNumber());
        preparedStatement.setInt(k++,ticket.getUserId());
        int ticketStatusId = getTicketStatusIdByName(ticket);
        preparedStatement.setInt(k,ticketStatusId);
    }

    @Override
    public Ticket findTicketById(int ticketId) throws DAOException {
        Optional<Ticket> ticket = Optional.empty();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.TICKETS_GET_TICKET_BY_ID)) {

            preparedStatement.setInt(1,ticketId);

            try(ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()){
                    ticket = Optional.ofNullable(ticketMapper
                            .extractFromResultSet(resultSet));
                }
            }
        }
        catch (SQLException e) {
            LOGGER.error("Ticket with ID : [{}] was not found. An exception occurs : {}",
                    ticketId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while loading Ticket by ID" + e.getMessage(), e);
        }
        return ticket.get();
    }

    @Override
    public List<Ticket> findTicketByUser(int userId) throws DAOException {

        List<Ticket> tickets = new ArrayList<>();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.TICKETS_GET_TICKET_BY_USER_ID)){

            preparedStatement.setInt(1, userId);

            try(ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()){
                    tickets.add(ticketMapper
                            .extractFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            LOGGER.error("Ticket with User ID : [{}] was not found. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while loading Ticket by User ID" + e.getMessage(), e);
        }


        return tickets;
    }

    @Override
    public List<Ticket> findAllTickets() throws DAOException {
        List<Ticket> tickets = new ArrayList<>();


        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.TICKETS_GET_ALL_TICKETS)
        ) {

            TicketMapper stationMapper = new TicketMapper();
            while (resultSet.next()){
                tickets.add(stationMapper.extractFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            LOGGER.error("Stations were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[StationDAO] exception while reading all tickets" + e.getMessage(), e);
        }

        return tickets;
    }


    private String getTicketStatusByTicketId(Ticket ticket, int ticketStatusId) throws SQLException {
        String name = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(Constants.GET_TICKET_STATUS_BY_TICKET_ID)) {

            preparedStatement.setInt(1, ticket.getId() );

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            LOGGER.error("Ticket status : [{}] was not found. An exception occurs." +
                            " Transaction rolled back!!! : {}",
                    ticket.getTicketStatus().getTicketStatusName(), e.getMessage());
            con.rollback();
            throw new DAOException("[TicketDAO] exception while reading Ticket status" + e.getMessage(), e);
        }
        return name;
    }

    private int getTicketStatusIdByName(Ticket ticket ) throws SQLException, DAOException {
        int ticketStatusId = 0;
        try (PreparedStatement preparedStatement = con.prepareStatement(Constants.GET_TICKET_STATUS_ID_BY_NAME)) {
            preparedStatement.setString(1, ticket.getTicketStatus().getTicketStatusName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ticketStatusId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.error("Ticket status : [{}] was not found. An exception occurs." +
                            " Transaction rolled back!!! : {}",
                    ticket.getTicketStatus().getTicketStatusName(), e.getMessage());
            con.rollback();
            throw new DAOException("[TicketDAO] exception while reading Ticket status" + e.getMessage(), e);
        }
        return ticketStatusId;
    }

}
