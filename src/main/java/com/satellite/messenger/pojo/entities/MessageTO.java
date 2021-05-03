package com.satellite.messenger.pojo.entities;

import com.satellite.messenger.pojo.enums.MessageStatusType;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Builder
public class MessageTO extends BaseEntityTO {

    private String value;

    private List<DistanceTO> distances;

    private MessageStatusType status;

    private String failedMessage;

}