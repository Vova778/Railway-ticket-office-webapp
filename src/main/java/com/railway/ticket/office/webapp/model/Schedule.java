package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Schedule implements Serializable {
    private int id;
    private Date date;
    private Train train;
    private List<Route> routes;

    public Schedule(int id, Date date, Train train) {
        this.id = id;
        this.date = date;
        this.train = train;
    }

    public Schedule(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Schedule() {

    }

    public int getId() {
        return id;
    }

    public Train getTrain() {
        return train;
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

    public void setTrain(Train train) {
        if (train == null) {
            throw new IllegalArgumentException("Train cannot be null");
        }
        this.train = train;
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
        if (!Objects.equals(date, schedule.date)) return false;
        if (!Objects.equals(train, schedule.train)) return false;
        return Objects.equals(routes, schedule.routes);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (train != null ? train.hashCode() : 0);
        result = 31 * result + (routes != null ? routes.hashCode() : 0);
        return result;
    }
}
