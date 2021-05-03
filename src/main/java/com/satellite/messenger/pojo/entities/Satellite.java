package com.satellite.messenger.pojo.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Data
@Entity(name = "product")
public class Satellite extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private double xPosition;

    @Column(nullable = false)
    private double yPosition;

}
