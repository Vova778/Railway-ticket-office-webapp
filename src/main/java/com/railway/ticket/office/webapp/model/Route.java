package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.sql.Time;

public class Route implements Serializable {
    private int id;
    private int stoppageNumber;
    private Station startingStation;
    private Station finalStation;
    private Schedule schedule;
    private Train train;
    private Time departureTime;
    private Time arrivalTime;
    private Time travelTime;
    private int availableSeats;
    private int day;
    private double price;

    public static Builder newBuilder() {
        return new Route().new Builder();
    }


    public Route() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id <0){
            throw new IllegalArgumentException("Id cannot be < 0");
        }
        this.id = id;
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

    public void setPrice(double price) {
        if(price<=0){
            throw new IllegalArgumentException("Price cannot be < 0");
        }
        this.price = price;
    }

    public void setSchedule(Schedule schedule) {
        if(schedule ==null){
            throw new IllegalArgumentException("Schedule id cannot be null");
        }
        this.schedule = schedule;
    }

    public void setAvailableSeats(int availableSeats) {
        if (availableSeats < 0) {
            throw new IllegalArgumentException("Available seats cannot be < 0");
        }
        this.availableSeats = availableSeats;
    }


    public void setDay(int day) {
        if(day<0){
            throw new IllegalArgumentException("Day cannot be < 0");
        }
        this.day = day;
    }

    public void setStoppageNumber(int stoppageNumber) {
        if (stoppageNumber <= 0) {
            throw new IllegalArgumentException("Stoppage number cannot be <= 0");
        }
        this.stoppageNumber = stoppageNumber;
    }

    public void setTrain(Train train) {
        if(train ==null){
            throw new IllegalArgumentException("Train cannot be null");
        }
        this.train = train;
    }

    public void setFinalStation(Station finalStation) {
        if (finalStation == null) {
            throw new IllegalArgumentException("Final station id cannot be < 0");
        }
        if (finalStation.getName().equals(startingStation.getName())) {
            throw new IllegalArgumentException("Final station cannot be the same as the starting station");
        }
        this.finalStation = finalStation;
    }

    public void setArrivalTime(Time arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Arrival time cannot be null");
        }
        this.arrivalTime = arrivalTime;
    }

    public void setStartingStation(Station startingStation) {
        if (startingStation== null) {
            throw new IllegalArgumentException("Starting station cannot be null");
        }
        this.startingStation = startingStation;
    }

    public void setDepartureTime(Time departureTime) {
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        }
        this.departureTime = departureTime;
    }

    public Time getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Time travelTime) {
        if (travelTime == null) {
            throw new IllegalArgumentException("Travel time cannot be null");
        }
        this.travelTime = travelTime;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startStation=" + startingStation +
                ", finalStation=" + finalStation +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", train=" + train +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != route.id) return false;
        if (stoppageNumber != route.stoppageNumber) return false;
        if (availableSeats != route.availableSeats) return false;
        if (day != route.day) return false;
        if (Double.compare(route.price, price) != 0) return false;
        if (startingStation != null ? !startingStation.equals(route.startingStation) : route.startingStation != null)
            return false;
        if (finalStation != null ? !finalStation.equals(route.finalStation) : route.finalStation != null) return false;
        if (schedule != null ? !schedule.equals(route.schedule) : route.schedule != null) return false;
        if (train != null ? !train.equals(route.train) : route.train != null) return false;
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
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (train != null ? train.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + availableSeats;
        result = 31 * result + day;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Train getTrain() {
        return train;
    }

    public Schedule getSchedule() {
        return schedule;
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



        public Builder setSchedule(Schedule schedule) {
            if(schedule==null){
                throw new IllegalArgumentException("Schedule cannot be null");
            }
            Route.this.schedule = schedule;
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
            
            if(day<0){
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

        public Builder setTrain(Train train) {
            if(train ==null){
                throw new IllegalArgumentException("Train cannot be null");
            }
            Route.this.train = train;
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
