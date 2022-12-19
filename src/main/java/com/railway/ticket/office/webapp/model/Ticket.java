package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Ticket implements Serializable {
    private int id;
    private double fare;
    private String startingStation;
    private String finalStation;
    private int trainNumber;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private Timestamp travelTime;
    private int userId;
    private TicketStatus ticketStatus;
    private List<Route> routes;





    public Ticket() {
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be < 0");
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }


    public double getFare() {
        return fare;
    }


    public Timestamp getDepartureTime() {
        return departureTime;
    }



    public Timestamp getArrivalTime() {
        return arrivalTime;
    }


    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setFare(double fare) {
        if (fare < 0 ) {
            throw new IllegalArgumentException("fare cannot be < 0 ");
        }
        this.fare = fare;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setStartingStation(String startingStation) {
        if (startingStation ==null) {
            throw new IllegalArgumentException("Starting station cannot be null");
        }
        this.startingStation = startingStation;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        if (finalStation ==null) {
            throw new IllegalArgumentException("Final station cannot be null");
        }
        this.finalStation = finalStation;
    }

    public Timestamp getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Timestamp travelTime) {
        if (travelTime == null) {
            throw new IllegalArgumentException("Travel time cannot be null");
        }
        this.travelTime = travelTime;
    }

    public void setTrainNumber(int trainNumber) {
        if (trainNumber <0) {
            throw new IllegalArgumentException("Train number cannot be < 0");
        }
        this.trainNumber = trainNumber;
    }
    public TicketStatus getById(int id) {
        for(TicketStatus t : TicketStatus.values())
            if(t.getId() == (id)) return t;
        return null;
    }
    public void setDepartureTime(Timestamp departureTime) {
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        }
        this.departureTime = departureTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Arrival time cannot be null");
        }

        this.arrivalTime = arrivalTime;
    }

    public void setUserId(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("User_ID cannot be < 0");
        }
        this.userId = userId;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        if (ticketStatus == null) {
            throw new IllegalArgumentException("Ticket status cannot be null");
        }
        this.ticketStatus = ticketStatus;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        if(routes ==null || routes.isEmpty()){
            throw new IllegalArgumentException("RouteList cannot be null");

        }
        this.routes = routes;
    }

    public enum TicketStatus {
        QUEUED("Queued",1),
        CLOSED("Closed",2),
        CANCELED("Canceled",3),
        REFUNDED("Refunded",4);

        private final String ticketStatusName;
        private final int id;
        TicketStatus(String ticketStatusName, int id) {
            this.ticketStatusName = ticketStatusName;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getTicketStatusName() {
            return ticketStatusName;
        }
    }



}
