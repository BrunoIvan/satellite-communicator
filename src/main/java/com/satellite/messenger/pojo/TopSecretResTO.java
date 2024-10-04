package com.satellite.messenger.pojo;

public class TopSecretResTO {

    private TopSecretResPosTO position;

    private String message;

    public TopSecretResTO() {
    }

    public TopSecretResTO(TopSecretResPosTO position, String message) {
        this.position = position;
        this.message = message;
    }

    public TopSecretResPosTO getPosition() {
        return position;
    }

    public void setPosition(TopSecretResPosTO position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
