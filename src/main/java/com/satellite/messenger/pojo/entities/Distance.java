package com.satellite.messenger.pojo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Entity(name = "distance")
public class Distance extends BaseEntity {

    @Column(nullable = false)
    private double distance;

    @Column
    private List<String> words;

    @ManyToOne
    @JoinColumn(name = "satellite_id")
    private Satellite satellite;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

}