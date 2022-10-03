package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Schedule implements Serializable {
    private int id;
    private Date date;
    private int trainId;
    private List<Route> routes;

    public Schedule(int id, Date date, int trainId) {
        this.id = id;
        this.date = date;
        this.trainId = trainId;
    }

    public Schedule() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if (date==null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.date = date;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        if (trainId < 0) {
            throw new IllegalArgumentException("Train cannot be < 0");
        }
        this.trainId = trainId;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        if (routes==null) {
            throw new IllegalArgumentException("Routes cannot be null");
        }
        this.routes = routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (id != schedule.id) return false;
        if (trainId != schedule.trainId) return false;
        if (date != null ? !date.equals(schedule.date) : schedule.date != null) return false;
        return routes != null ? routes.equals(schedule.routes) : schedule.routes == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + trainId;
        result = 31 * result + (routes != null ? routes.hashCode() : 0);
        return result;
    }
}
