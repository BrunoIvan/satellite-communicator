package com.satellite.messenger.pojo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@AllArgsConstructor
public class SatelliteTO extends BaseEntityTO {

    private String name;

    private double xPosition;

    private double yPosition;

}
