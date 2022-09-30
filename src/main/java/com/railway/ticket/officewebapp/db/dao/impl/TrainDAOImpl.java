package com.railway.ticket.officewebapp.db.dao.impl;

import com.railway.ticket.officewebapp.db.Constants;
import com.railway.ticket.officewebapp.db.DBException;
import com.railway.ticket.officewebapp.db.dao.TrainDAO;
import com.railway.ticket.officewebapp.db.dao.mapper.impl.TrainMapper;
import com.railway.ticket.officewebapp.model.Station;
import com.railway.ticket.officewebapp.model.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainDAOImpl implements TrainDAO {


    private final Connection con;
    private static final Logger LOGGER = LogManager.getLogger(TrainDAOImpl.class);

    public TrainDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void insertTrain(Train train) throws DBException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.TRAINS_INSERT_TRAIN)) {

            preparedStatement.setInt(1,train.getSeats());

            preparedStatement.executeUpdate();
            LOGGER.info("Train : {} was inserted successfully", train);
        } catch (SQLException e) {
            LOGGER.error("Train : [{}] was not inserted. An exception occurs : {}",
                    train, e.getMessage());
            throw new DBException("[TrainDAO] exception while creating Train" + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTrain(int trainId) throws DBException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.TRAINS_DELETE_TRAIN)) {

            preparedStatement.setInt(1,trainId);

            int removedRow = preparedStatement.executeUpdate();

            if(removedRow>0){
                LOGGER.info("Train with ID : {} was removed successfully", trainId);
            }

        } catch (SQLException e) {
            LOGGER.error("Train with ID : [{}] was not removed. An exception occurs : {}",
                    trainId, e.getMessage());
            throw new DBException("[TrainDAO] exception while removing Train" + e.getMessage(), e);
        }
    }

    @Override
    public void updateTrain(int trainId, Train train) throws DBException {
        try(PreparedStatement preparedStatement =
                    con.prepareStatement(Constants.TRAINS_UPDATE_TRAIN)) {

            int k = 1;

            preparedStatement.setInt(k++,train.getSeats());
            preparedStatement.setInt(k,trainId);


            int updatedRow = preparedStatement.executeUpdate();
            if(updatedRow>0){
                LOGGER.info("Train with ID : {} was updated successfully", trainId);
            }

        } catch (SQLException e) {
            LOGGER.error("Train with ID : [{}] was not updated. An exception occurs : {}",
                    trainId, e.getMessage());
            throw new DBException("[TrainDAO] exception while updating Train" + e.getMessage(), e);
        }
    }

    @Override
    public Train getTrainById(int trainId) throws DBException {
        Optional<Train> train = Optional.empty();

        try(PreparedStatement preparedStatement
                    = con.prepareStatement(Constants.TRAINS_GET_TRAIN_BY_ID)) {

            preparedStatement.setInt(1,trainId);

            try(ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()){
                    train = Optional.ofNullable(new TrainMapper()
                            .extractFromResultSet(resultSet));
                }
            }

        }
        catch (SQLException e) {
            LOGGER.error("Train with ID : [{}] was not found. An exception occurs : {}",
                    trainId, e.getMessage());
            throw new DBException("[TrainDAO] exception while loading Train" + e.getMessage(), e);
        }
        return train.get();
    }

    @Override
    public List<Train> getTrainBetweenStations(Station startStation, Station endStation) throws DBException {
        List<Train> trains = new ArrayList<>();

        try(Statement statement = con.createStatement();
            ResultSet resultSet
                    = statement.executeQuery(Constants.TRAINS_GET_ALL_TRAINS)
        ) {

            TrainMapper trainMapper = new TrainMapper();
            while (resultSet.next()){
                trains.add(trainMapper.extractFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            LOGGER.error("Trains were not found. An exception occurs : {}", e.getMessage());
            throw new DBException("[TrainDAO] exception while reading all trains" + e.getMessage(), e);
        }

        return trains;
    }

    @Override
    public List<Train> findAllTrains() throws DBException {
        List<Train> trains = new ArrayList<>();


        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.TRAINS_GET_ALL_TRAINS)
        ) {

            TrainMapper trainMapper = new TrainMapper();
            while (resultSet.next()){
                trains.add(trainMapper.extractFromResultSet(resultSet));
            }
        }
        catch (SQLException e) {
            LOGGER.error("Trains were not found. An exception occurs : {}", e.getMessage());
            throw new DBException("[TrainDAO] exception while reading all trains" + e.getMessage(), e);
        }

        return trains;
    }
}
