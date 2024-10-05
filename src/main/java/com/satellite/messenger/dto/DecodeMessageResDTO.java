package com.satellite.messenger.dto;

import java.io.Serializable;

public class DecodeMessageResDTO implements Serializable {

    private DecodeMessagePosDTO position;

    private String message;

    public DecodeMessageResDTO(DecodeMessagePosDTO position, String message) {
        this.position = position;
        this.message = message;
    }

    public DecodeMessagePosDTO getPosition() {
        return position;
    }

    public void setPosition(DecodeMessagePosDTO position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
