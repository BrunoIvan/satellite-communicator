package com.satellite.messenger.pojo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class TopSecretReqTO {

    @NotEmpty
    @Size(min = 3, max = 3)
    private List<TopSecretReqItemTO> satellites;

    public TopSecretReqTO() {
    }

    public TopSecretReqTO(List<TopSecretReqItemTO> satellites) {
        this.satellites = satellites;
    }

    public @NotEmpty @Size(min = 3, max = 3) List<TopSecretReqItemTO> getSatellites() {
        return satellites;
    }

    public void setSatellites(@NotEmpty @Size(min = 3, max = 3) List<TopSecretReqItemTO> satellites) {
        this.satellites = satellites;
    }
}
