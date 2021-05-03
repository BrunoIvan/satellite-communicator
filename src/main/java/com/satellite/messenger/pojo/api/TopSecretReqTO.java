package com.satellite.messenger.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretReqTO {

    @NotEmpty
    @Size(min = 3, max = 3)
    private List<TopSecretReqItemTO> satellites;

}
