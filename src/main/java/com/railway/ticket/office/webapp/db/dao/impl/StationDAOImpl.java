package com.railway.ticket.office.webapp.db.dao.impl;

import com.railway.ticket.office.webapp.db.Constants;
import com.railway.ticket.office.webapp.db.dao.mapper.impl.StationMapper;
import com.railway.ticket.office.webapp.exceptions.DAOException;
import com.railway.ticket.office.webapp.model.Station;
import com.railway.ticket.office.webapp.db.dao.StationDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationDAOImpl implements StationDAO {

    private final Connection con;
    private static final Logger log = LogManager.getLogger(StationDAOImpl.class);
    private final StationMapper stationMapper = new StationMapper();

    public StationDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public Connection getConnection() {
        return con;
    }
    @Override
    public int insertStation(Station station) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.STATIONS_INSERT_STATION,
                            Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;

            preparedStatement.setString(k,station.getName());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int key = 0;
            if (resultSet.next()) {
                key = resultSet.getInt(1);
            }

            log.info("Station : {} was inserted successfully", station);
            return key;

        } catch (SQLException e) {
            log.error("Station : [{}] was not inserted. An exception occurs : {}",
                    station, e.getMessage());
            throw new DAOException("[StationDAO] exception while creating Station" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteStation(int stationId) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.STATIONS_DELETE_STATION)) {

            preparedStatement.setInt(1,stationId);

            int removedRow = preparedStatement.executeUpdate();

            if(removedRow>0){
                log.info("Station with ID : {} was removed successfully", stationId);
            }

        } catch (SQLException e) {
            log.error("Station with ID : [{}] was not removed. An exception occurs : {}",
                    stationId, e.getMessage());
            throw new DAOException("[StationDAO] exception while removing Station" + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateStation(int stationId, Station station) throws DAOException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.STATIONS_UPDATE_STATION)) {

            int k = 1;
            preparedStatement.setString(k++,station.getName());
            preparedStatement.setInt(k,stationId);
            int updatedRow = preparedStatement.executeUpdate();
            if(updatedRow>0){
                log.info("Station with ID : {} was updated successfully", stationId);
                return true;
            } else {
                log.info("Station with ID : [{}] was not found for update", stationId);
                return false;
            }

        } catch (SQLException e) {
            log.error("Station with ID : [{}] was not updated. An exception occurs : {}",
                    stationId, e.getMessage());
            throw new DAOException("[StationDAO] exception while updating Station" + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Station> findStationById(int stationId) throws DAOException {
        Optional<Station> station = Optional.empty();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.STATIONS_GET_STATION_BY_ID)) {

            preparedStatement.setInt(1,stationId);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    station = Optional.ofNullable(stationMapper
                            .extractFromResultSet(resultSet));
                }
            }
            station.ifPresent(s -> log.info("Station was received: [{}]", s));

            return station;

        }
        catch (SQLException e) {
            log.error("Station with ID : [{}] was not found. An exception occurs : {}",
                    stationId, e.getMessage());
            throw new DAOException("[StationDAO] exception while loading Station by ID" + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Station> findStationByName(String stationName) throws DAOException {
        Optional<Station>  station = Optional.empty();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.STATIONS_GET_STATION_BY_NAME)) {

            preparedStatement.setString(1,stationName);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    station = Optional.ofNullable(stationMapper
                            .extractFromResultSet(resultSet));
                }
            }
            station.ifPresent(s -> log.info("Station was received: [{}]", s));

            return station;
        }
        catch (SQLException e) {
            log.error("Station with name : [{}] was not found. An exception occurs : {}",
                    stationName, e.getMessage());
            throw new DAOException("[StationDAO] exception while loading Station by name" + e.getMessage(), e);
        }

    }

    @Override
    public List<Station> findAllStations(int offset) throws DAOException {
        List<Station> stations = new ArrayList<>();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.STATIONS_GET_ALL_STATIONS_WITH_OFFSET)) {

            preparedStatement.setInt(1, offset);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    stations.add(stationMapper
                            .extractFromResultSet(resultSet));
                }
            }
            return stations;
        }
        catch (SQLException e) {
            log.error("Stations were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[StationDAO] exception while reading all stations" + e.getMessage(), e);
        }
    }

    @Override
    public List<Station> findAllStations() throws DAOException {
        List<Station> stations = new ArrayList<>();

        try(Statement statement
                    = con.createStatement()) {

            try(ResultSet resultSet =
                        statement.executeQuery(Constants.STATIONS_GET_ALL_STATIONS)){
                while (resultSet.next()){
                    stations.add(stationMapper
                            .extractFromResultSet(resultSet));
                }
            }
            return stations;
        }
        catch (SQLException e) {
            log.error("Stations were not found. An exception occurs : {}", e.getMessage());
            throw new DAOException("[StationDAO] exception while reading all stations" + e.getMessage(), e);
        }
    }

    @Override
    public int countRecords() {
        int recordsCount = 0;
        try (PreparedStatement preparedStatement =
                     con.prepareStatement(Constants.STATIONS_GET_COUNT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            recordsCount = resultSet.getInt(1);
            return recordsCount;
        } catch (SQLException e) {
            log.error("[StationDAO] Failed to count stations! An exception occurs :[{}]",
                    e.getMessage());
        }
        return recordsCount;
    }

}
