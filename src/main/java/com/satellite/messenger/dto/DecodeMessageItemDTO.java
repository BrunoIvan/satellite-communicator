package com.satellite.messenger.dto;

import java.io.Serializable;
import java.util.List;

public class DecodeMessageItemDTO implements Serializable {

    private String name;

    private double distance;

    private List<String> message;

    public DecodeMessageItemDTO() {
    }

    public DecodeMessageItemDTO(String name, double distance, List<String> message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
