package com.satellite.messenger.services;

import com.satellite.messenger.persistence.stores.SatelliteStore;
import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResPosTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.pojo.entities.SatelliteTO;
import com.satellite.messenger.utils.LocationUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.satellite.messenger.utils.LocationUtils.round;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.collections.Lists.newArrayList;

@Test
public class TopSecretServiceTest {

    @InjectMocks
    public TopSecretService topSecretService;

    @Mock
    public SatelliteStore satelliteStore;

    @BeforeClass
    public void init() {
        openMocks(this);
    }

    public void decodeOk() {
        final TopSecretReqTO request = new TopSecretReqTO(newArrayList(
                new TopSecretReqItemTO("Kenobi", 600, newArrayList("", "Este", "es", "", "mensaje")),
                new TopSecretReqItemTO("Skywalker", 115.5, newArrayList("Este", "", "un", "mensaje")),
                new TopSecretReqItemTO("Sato", 109.856054, newArrayList("", "Este", "", "", "mensaje"))
        ));

        when(satelliteStore.getAll()).thenReturn(newArrayList(
                new SatelliteTO("Kenobi", -500, -200),
                new SatelliteTO("Skywalker", 100, -100),
                new SatelliteTO("Sato", 170, -300)
        ));

        final TopSecretResTO expectedResult = new TopSecretResTO(new TopSecretResPosTO(99.799763, -215.499826), "Este es un mensaje");
        final TopSecretResTO result = topSecretService.decodeResponse(request);

        result.getPosition().setX(round(result.getPosition().getX(), 6));
        result.getPosition().setY(round(result.getPosition().getY(), 6));

        Assert.assertEquals(result, expectedResult);
    }

}
