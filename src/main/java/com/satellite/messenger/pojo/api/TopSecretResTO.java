package com.satellite.messenger.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretResTO {

    private TopSecretResPosTO position;

    private String message;

}
