package com.railway.ticket.office.webapp.db.dao.impl;

import com.railway.ticket.office.webapp.db.Constants;
import com.railway.ticket.office.webapp.db.dao.TicketDAO;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.TicketMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Route;
import com.railway.ticket.office.webapp.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDAOImpl implements TicketDAO {

    private final Connection con;
    private static final Logger log = LogManager.getLogger(TicketDAOImpl.class);
    private final TicketMapper ticketMapper = new TicketMapper();

    public TicketDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Connection getConnection() {
        return con;
    }

    @Override
    public int insert(Ticket ticket) throws DAOException {

        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.TICKETS_INSERT_TICKET,
                             Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            setTicketParameters(ticket, preparedStatement);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int key = 0;
            if (resultSet.next()) {
                key = resultSet.getInt(1);
                log.info("Ticket : {} was inserted successfully", ticket);
            }
            setRoutesForTicket(ticket);


            log.info("Routes for ticket: {} were inserted successfully", ticket);
            con.commit();
            return key;
        } catch (SQLException e) {

            try {
                con.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            log.error("Ticket : [{}] was not inserted. An exception occurs : {}",
                    ticket, e.getMessage());
            throw new DAOException("[TicketDAO] exception while creating Ticket" + e.getMessage(), e);
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }


    private void setRoutesForTicket(Ticket ticket) throws DAOException {
        if (ticket == null) return;
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.TICKETS_INSERT_TICKET_ROUTES)) {

            preparedStatement.setInt(1, ticket.getId());
            preparedStatement.setInt(2, ticket.getUserId());
            preparedStatement.setInt(3, ticket.getTicketStatus().getId());


            for (Route r : ticket.getRoutes()) {
                int k = 4;

                preparedStatement.setInt(k++, r.getId());
                preparedStatement.setInt(k++, r.getStartingStation().getId());
                preparedStatement.setInt(k++, r.getFinalStation().getId());
                preparedStatement.setInt(k++, r.getSchedule().getId());
                preparedStatement.setInt(k, r.getTrain().getNumber());
                preparedStatement.executeUpdate();
            }


        } catch (SQLException e) {
            log.error("Ticket : [{}] was not inserted. An exception occurs : {}",
                    ticket, e.getMessage());
            throw new DAOException("[TicketDAO] exception while creating Ticket" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int ticketId) throws DAOException {
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.TICKETS_DELETE_TICKET)) {

            preparedStatement.setInt(1, ticketId);

            int removedRow = preparedStatement.executeUpdate();

            if (removedRow > 0) {
                log.info("Ticket with ID : {} was removed successfully", ticketId);
            }

        } catch (SQLException e) {
            log.error("Ticket with ID : [{}] was not removed. An exception occurs : {}",
                    ticketId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while removing Ticket" + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Ticket ticket) throws DAOException {
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.TICKETS_UPDATE_TICKET)) {

            setTicketParameters(ticket, preparedStatement);

            preparedStatement.setInt(9, ticket.getId());

            int updatedRow = preparedStatement.executeUpdate();

            if (updatedRow > 0) {
                log.info("Ticket with ID : {} was updated successfully", ticket.getId());
                return true;
            } else {
                log.info("Ticket with ID : [{}] was not found for update", ticket.getId());
                return false;
            }

        } catch (SQLException e) {
            log.error("Ticket with ID : [{}] was not updated. An exception occurs : {}",
                    ticket.getId(), e.getMessage());
            throw new DAOException("[TicketDAO] exception while updating Ticket" + e.getMessage(), e);
        }
    }

    private void setTicketParameters(Ticket ticket, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setDouble(k++, ticket.getFare());
        preparedStatement.setString(k++, ticket.getStartingStation());
        preparedStatement.setString(k++, ticket.getFinalStation());
        preparedStatement.setTimestamp(k++, ticket.getDepartureTime());
        preparedStatement.setTimestamp(k++, ticket.getArrivalTime());
        preparedStatement.setInt(k++, ticket.getTrainNumber());
        preparedStatement.setInt(k++, ticket.getUserId());
        preparedStatement.setInt(k, ticket.getTicketStatus().getId());
    }

    @Override
    public Ticket findById(int ticketId) throws DAOException {
        Optional<Ticket> ticket = Optional.empty();

        try (PreparedStatement preparedStatement
                     = con.prepareStatement(Constants.TICKETS_GET_TICKET_BY_ID)) {

            preparedStatement.setInt(1, ticketId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ticket = Optional.ofNullable(ticketMapper
                            .extractFromResultSet(resultSet));
                }
            }
            ticket.ifPresent(t -> log.info("Ticket was received: [{}]", t));

            return ticket.orElse(new Ticket());
        } catch (SQLException e) {
            log.error("Ticket with ID : [{}] was not found. An exception occurs : {}",
                    ticketId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while loading Ticket by ID" + e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> findTicketByUser(int userId) throws DAOException {

        List<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement preparedStatement
                     = con.prepareStatement(Constants.TICKETS_GET_TICKET_BY_USER_ID)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(ticketMapper
                            .extractFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            log.error("Ticket with User ID : [{}] was not found. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while loading Ticket by User ID" + e.getMessage(), e);
        }


        return tickets;
    }

    @Override
    public List<Ticket> findTicketByUser(int userId, int offset) throws DAOException {

        List<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement preparedStatement
                     = con.prepareStatement(Constants.TICKETS_GET_TICKET_BY_USER_ID_WITH_OFFSET)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, offset);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(ticketMapper
                            .extractFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            log.error("Ticket with User ID : [{}] was not found. An exception occurs : {}",
                    userId, e.getMessage());
            throw new DAOException("[TicketDAO] exception while loading Ticket by User ID" + e.getMessage(), e);
        }


        return tickets;
    }

    @Override
    public List<Ticket> findAll() throws DAOException {
        List<Ticket> tickets = new ArrayList<>();


        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(Constants.TICKETS_GET_ALL_TICKETS)
        ) {

            TicketMapper stationMapper = new TicketMapper();
            while (resultSet.next()) {
                tickets.add(stationMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error("Stations were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[StationDAO] exception while reading all tickets" + e.getMessage(), e);
        }

        return tickets;
    }


    @Override
    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.TICKETS_GET_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            log.error("[TicketDAO] Failed to count tickets!" +
                            " An exception occurs :[{}]",
                    e.getMessage());
        }
        return recordsCount;
    }

}
