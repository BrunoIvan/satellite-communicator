package com.satellite.messenger.pojo.entities;

import com.satellite.messenger.pojo.enums.MessageStatusType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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