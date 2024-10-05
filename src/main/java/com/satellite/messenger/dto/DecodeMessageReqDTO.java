package com.satellite.messenger.dto;

import java.io.Serializable;
import java.util.List;

public class DecodeMessageReqDTO implements Serializable {

    private List<DecodeMessageItemDTO> satellites;

    public DecodeMessageReqDTO() {
    }

    public DecodeMessageReqDTO(List<DecodeMessageItemDTO> satellites) {
        this.satellites = satellites;
    }

    public List<DecodeMessageItemDTO> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<DecodeMessageItemDTO> satellites) {
        this.satellites = satellites;
    }
}
