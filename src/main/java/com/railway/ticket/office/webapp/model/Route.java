package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.sql.Time;

public class Route implements Serializable {
    private int id;
    private int stoppageNumber;
    private Station startingStation;
    private Station finalStation;
    private int scheduleId;
    private int trainNumber;
    private Time departureTime;
    private Time arrivalTime;
    private int availableSeats;
    private int day;
    private double price;
    /*
    private Schedule schedule;
    private Train train;*/




    public static Builder newBuilder() {
        return new Route().new Builder();
    }


    public Route() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(scheduleId<0){
            throw new IllegalArgumentException("Schedule id cannot be < 0");
        }
        this.id = id;
    }


    public int getScheduleId() {
        return scheduleId;
    }

    public Station getStartingStation() {
        return startingStation;
    }

    public Station getFinalStation() {
        return finalStation;
    }


    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public int getStoppageNumber() {
        return stoppageNumber;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public int getDay() {
        return day;
    }


    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startStation=" + startingStation +
                ", finalStation=" + finalStation +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", train=" + trainNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != route.id) return false;
        if (stoppageNumber != route.stoppageNumber) return false;
        if (scheduleId != route.scheduleId) return false;
        if (trainNumber != route.trainNumber) return false;
        if (availableSeats != route.availableSeats) return false;
        if (day != route.day) return false;
        if (Double.compare(route.price, price) != 0) return false;
        if (startingStation != null ? !startingStation.equals(route.startingStation) : route.startingStation != null)
            return false;
        if (finalStation != null ? !finalStation.equals(route.finalStation) : route.finalStation != null) return false;
        if (departureTime != null ? !departureTime.equals(route.departureTime) : route.departureTime != null)
            return false;
        return arrivalTime != null ? arrivalTime.equals(route.arrivalTime) : route.arrivalTime == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + stoppageNumber;
        result = 31 * result + (startingStation != null ? startingStation.hashCode() : 0);
        result = 31 * result + (finalStation != null ? finalStation.hashCode() : 0);
        result = 31 * result + scheduleId;
        result = 31 * result + trainNumber;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + availableSeats;
        result = 31 * result + day;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public class Builder {

        private Builder() {}

        public Builder setPrice(double price) {
            if(price<=0){
                throw new IllegalArgumentException("Price cannot be < 0");
            }
            Route.this.price = price;
            return this;
        }



        public Builder setScheduleId(int scheduleId) {
            if(scheduleId<0){
                throw new IllegalArgumentException("Schedule id cannot be < 0");
            }
            Route.this.scheduleId = scheduleId;
            return this;
        }

        public Builder setAvailableSeats(int availableSeats) {
            if (availableSeats < 0) {
                throw new IllegalArgumentException("Available seats cannot be < 0");
            }
            Route.this.availableSeats = availableSeats;
            return this;
        }


        public Builder setDay(int day) {
            
            if(day<=0){
                throw new IllegalArgumentException("Day cannot be < 0");
            }
            Route.this.day = day;
            return this;
        }

        public Builder setStoppageNumber(int stoppageNumber) {
            if (stoppageNumber <= 0) {
                throw new IllegalArgumentException("Stoppage number cannot be <= 0");
            }
            Route.this.stoppageNumber = stoppageNumber;
            return this;
        }

        public Builder setTrainNumber(int trainNumber) {
            if(Route.this.trainNumber <0){
                throw new IllegalArgumentException("Train cannot be <0");
            }
            Route.this.trainNumber = trainNumber;
            return this;
        }

        public Builder setFinalStation(Station finalStation) {
            if (finalStation == null) {
                throw new IllegalArgumentException("Final station id cannot be < 0");
            }
            if (finalStation.getName().equals(startingStation.getName())) {
                throw new IllegalArgumentException("Final station cannot be the same as the starting station");
            }

            Route.this.finalStation = finalStation;
            return this;
        }

        public Builder setArrivalTime(Time arrivalTime) {
            if (arrivalTime == null) {
                throw new IllegalArgumentException("Arrival time cannot be null");
            }
            Route.this.arrivalTime = arrivalTime;
            return this;
        }

        public Builder setStartingStation(Station startingStation) {
            if (startingStation== null) {
                throw new IllegalArgumentException("Starting station cannot be null");
            }
            Route.this.startingStation = startingStation;
            return this;
        }

        public Builder setDepartureTime(Time departureTime) {
            if (departureTime == null) {
                throw new IllegalArgumentException("Departure time cannot be null");
            }

            Route.this.departureTime = departureTime;
            return this;
        }

        public Builder setId(int id) {
            if (id < 0) {
                throw new IllegalArgumentException("ID cannot be < 0");
            }
            Route.this.id = id;
            return this;
        }


        public Route build() {
            return Route.this;
        }

    }

}
