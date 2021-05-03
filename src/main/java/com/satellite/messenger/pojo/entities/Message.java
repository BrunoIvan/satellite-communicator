package com.satellite.messenger.pojo.entities;

import com.satellite.messenger.pojo.enums.MessageStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Entity(name = "message")
public class Message extends BaseEntity {

    @Column
    private String value;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distance> distances;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageStatusType status;

    @Column
    private String failedMessage;

}