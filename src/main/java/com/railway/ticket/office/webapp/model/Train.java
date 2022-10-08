package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.util.List;

public class Train implements Serializable {
    private int number;
    private int seats;
    private List<Schedule> schedules;

    public Train(int number, int seats) {
        this.number = number;
        this.seats = seats;
    }

    public Train() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be < 0");
        }
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        if (seats < 0) {
            throw new IllegalArgumentException("Seats cannot be < 0");
        }
        this.seats = seats;
    }




    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        if (schedules == null) {
        throw new IllegalArgumentException("Schedules can't be null!");
    }
        this.schedules = schedules;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (number != train.number) return false;
        return seats == train.seats;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + seats;
        return result;
    }

    @Override
    public String toString() {
        return "Train{" +
                "number=" + number +
                ", seats=" + seats +
                ", schedules=" + schedules +
                '}';
    }
}
