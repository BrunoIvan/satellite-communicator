package com.satellite.messenger.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretReqItemTO {

    @NotBlank
    private String name;

    @NotNull
    private double distance;

    @NotEmpty
    private List<String> message;
}
