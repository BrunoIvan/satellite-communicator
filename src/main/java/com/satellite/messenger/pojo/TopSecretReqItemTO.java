package com.satellite.messenger.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TopSecretReqItemTO {

    @NotBlank
    private String name;

    @NotNull
    private double distance;

    @NotEmpty
    private List<String> message;

    public TopSecretReqItemTO(String name, double distance, List<String> message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    @NotNull
    public double getDistance() {
        return distance;
    }

    public void setDistance(@NotNull double distance) {
        this.distance = distance;
    }

    public @NotEmpty List<String> getMessage() {
        return message;
    }

    public void setMessage(@NotEmpty List<String> message) {
        this.message = message;
    }
}
