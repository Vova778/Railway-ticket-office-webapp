package com.railway.ticket.office.webapp.model;

import java.io.Serializable;
import java.util.List;

public class Station implements Serializable {
    private int id;
    private String name;
    private List<Station> connectingStations;

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
    }



    public Station() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (name.length()<=2) {
            throw new IllegalArgumentException("Name length cannot be less than 3");
        }
        this.name = name;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be < 0");
        }
        this.id = id;
    }

    public List<Station> getConnectingStations() {
        return connectingStations;
    }

    public void setConnectingStations(List<Station> connectingStations) {
        if (connectingStations == null) {
            throw new IllegalArgumentException("Connecting stations cannot be null");
        }
        this.connectingStations = connectingStations;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != station.id) return false;
        if (name != null ? !name.equals(station.name) : station.name != null) return false;
        return connectingStations != null ? connectingStations.equals(station.connectingStations) : station.connectingStations == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (connectingStations != null ? connectingStations.hashCode() : 0);
        return result;
    }
}
