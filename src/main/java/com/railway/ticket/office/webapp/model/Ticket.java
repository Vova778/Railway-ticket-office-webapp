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
        if (startingStation ==null||startingStation.equals("")) {
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
        if (finalStation ==null||finalStation.equals("")) {
            throw new IllegalArgumentException("Final station cannot be null");
        }
        this.finalStation = finalStation;
    }


    public void setTrainNumber(int trainNumber) {
        if (trainNumber < 0) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (Double.compare(ticket.fare, fare) != 0) return false;
        if (trainNumber != ticket.trainNumber) return false;
        if (userId != ticket.userId) return false;
        if (startingStation != null ? !startingStation.equals(ticket.startingStation) : ticket.startingStation != null)
            return false;
        if (finalStation != null ? !finalStation.equals(ticket.finalStation) : ticket.finalStation != null)
            return false;
        if (departureTime != null ? !departureTime.equals(ticket.departureTime) : ticket.departureTime != null)
            return false;
        if (arrivalTime != null ? !arrivalTime.equals(ticket.arrivalTime) : ticket.arrivalTime != null) return false;
        if (ticketStatus != ticket.ticketStatus) return false;
        return routes != null ? routes.equals(ticket.routes) : ticket.routes == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(fare);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startingStation != null ? startingStation.hashCode() : 0);
        result = 31 * result + (finalStation != null ? finalStation.hashCode() : 0);
        result = 31 * result + trainNumber;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (ticketStatus != null ? ticketStatus.hashCode() : 0);
        result = 31 * result + (routes != null ? routes.hashCode() : 0);
        return result;
    }
}
