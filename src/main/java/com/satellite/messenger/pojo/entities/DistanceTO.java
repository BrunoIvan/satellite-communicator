package com.satellite.messenger.pojo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@AllArgsConstructor
public class DistanceTO extends BaseEntityTO {

    private double value;

    private List<String> words;

    private SatelliteTO satellite;

    private MessageTO message;
}
