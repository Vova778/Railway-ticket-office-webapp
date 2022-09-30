package com.railway.ticket.officewebapp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Ticket implements Serializable {
    private int id;
    private double fare;
    private int startingStationId;
    private int finalStationId;
    private int trainNumber;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private int userId;
    private TicketStatus ticketStatus;
 /*   private Station startingStation;
    private Station finalStation;
    private Train train;*/

    public static Ticket.Builder newBuilder() {
        return new Ticket().new Builder();
    }


    public Ticket() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (Double.compare(ticket.fare, fare) != 0) return false;
        if (startingStationId != ticket.startingStationId) return false;
        if (finalStationId != ticket.finalStationId) return false;
        if (trainNumber != ticket.trainNumber) return false;
        if (userId != ticket.userId) return false;
        if (!Objects.equals(departureTime, ticket.departureTime))
            return false;
        if (!Objects.equals(arrivalTime, ticket.arrivalTime)) return false;
        return ticketStatus == ticket.ticketStatus;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(fare);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + startingStationId;
        result = 31 * result + finalStationId;
        result = 31 * result + trainNumber;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (ticketStatus != null ? ticketStatus.hashCode() : 0);
        return result;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }


    public int getStartingStationId() {
        return startingStationId;
    }

    public int getFinalStationId() {
        return finalStationId;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public enum TicketStatus {
        QUEUED("QUEUED"),
        CLOSED("CLOSED");

        private final String ticketStatusName;

        TicketStatus(String ticketStatusName) {
            this.ticketStatusName = ticketStatusName;
        }

        public String getTicketStatusName() {
            return ticketStatusName;
        }
    }

    public class Builder {

        private Builder() {}


        public Builder setId(int id) {
            if (id < 0) {
                throw new IllegalArgumentException("ID cannot be < 0");
            }
            Ticket.this.id = id;
            return this;
        }

        public Builder setUserId(int userId) {
            if (userId < 0) {
                throw new IllegalArgumentException("User_ID cannot be < 0");
            }
            Ticket.this.userId = userId;
            return this;
        }

        public Builder setTrainNumber(int trainNumber) {
            if (trainNumber < 0) {
                throw new IllegalArgumentException("Train number cannot be < 0");
            }
            Ticket.this.trainNumber = trainNumber;
            return this;
        }



        public Builder setFare(double fare) {
            if (fare < 0 ) {
                throw new IllegalArgumentException("fare cannot be < 0 ");
            }
            Ticket.this.fare = fare;
            return this;
        }


        public Builder setStartingStationId(int startingStationId) {
            if (startingStationId < 0) {
                throw new IllegalArgumentException("Starting station id cannot be < 0");
            }
            Ticket.this.startingStationId = startingStationId;
            return this;
        }


        public Builder setFinalStationId(int finalStationId) {
            if (finalStationId < 0) {
                throw new IllegalArgumentException("Final station id cannot be < 0");
            }
            Ticket.this.finalStationId = finalStationId;
            return this;
        }


        public Builder setTicketStatus(TicketStatus ticketStatus) {
            if (ticketStatus == null) {
                throw new IllegalArgumentException("Ticket status cannot be null");
            }
            Ticket.this.ticketStatus = ticketStatus;
            return this;
        }

        public Builder setDepartureTime(Timestamp departureTime) {
            if (departureTime == null) {
                throw new IllegalArgumentException("Departure time cannot be null");
            }

             Ticket.this.departureTime = departureTime;
            return this;
        }

        public Builder setArrivalTime(Timestamp arrivalTime) {
            if (arrivalTime == null) {
                throw new IllegalArgumentException("Arrival time cannot be null");
            }

            Ticket.this.arrivalTime = arrivalTime;
            return this;
        }



        public Ticket build() {
            return Ticket.this;
        }

    }


}
