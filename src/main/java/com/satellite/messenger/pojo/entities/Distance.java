package com.satellite.messenger.pojo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Entity(name = "distance")
public class Distance extends BaseEntity {

    @Column(nullable = false)
    private double distance;

    @ElementCollection
    @CollectionTable(name="word", joinColumns = @JoinColumn(name="distance_id"))
    @Column
    private List<String> words;

    @ManyToOne
    @JoinColumn(name = "satellite_id")
    private Satellite satellite;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

}