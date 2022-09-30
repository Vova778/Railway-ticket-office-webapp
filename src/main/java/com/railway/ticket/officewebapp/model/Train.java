package com.railway.ticket.officewebapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Train implements Serializable {
    private int id;
    private int seats;
    private Map<Date, Schedule> schedules;

    public Train(int id, int seats) {
        this.id = id;
        this.seats = seats;
    }

    public Train() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be < 0");
        }
        this.id = id;
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




    public Map<Date, Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Map<Date, Schedule> schedules) {
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

        if (id != train.id) return false;
        return seats == train.seats;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + seats;
        return result;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", totalSeats=" + seats +
                ", routes=" + schedules +
                '}';
    }
}
