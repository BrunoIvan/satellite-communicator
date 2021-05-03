package com.satellite.messenger.pojo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class SatelliteTO {

    private String name;

    private double xPosition;

    private double yPosition;

}
