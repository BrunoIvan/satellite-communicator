package com.satellite.messenger.pojo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Entity(name = "satellite")
public class Satellite extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private double xPosition;

    @Column(nullable = false)
    private double yPosition;

}
