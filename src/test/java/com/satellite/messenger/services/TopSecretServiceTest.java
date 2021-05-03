package com.satellite.messenger.services;

import com.satellite.messenger.persistence.stores.DistanceStore;
import com.satellite.messenger.persistence.stores.MessageStore;
import com.satellite.messenger.persistence.stores.SatelliteStore;
import com.satellite.messenger.pojo.api.TopSecretReqItemTO;
import com.satellite.messenger.pojo.api.TopSecretReqTO;
import com.satellite.messenger.pojo.api.TopSecretResPosTO;
import com.satellite.messenger.pojo.api.TopSecretResTO;
import com.satellite.messenger.pojo.entities.DistanceTO;
import com.satellite.messenger.pojo.entities.MessageTO;
import com.satellite.messenger.pojo.entities.SatelliteTO;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.satellite.messenger.utils.LocationUtils.round;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.collections.Lists.newArrayList;

@Test
public class TopSecretServiceTest {

    protected static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    public TopSecretService topSecretService;

    @Mock
    public SatelliteStore satelliteStore;

    @Mock
    public DistanceStore distanceStore;

    @Mock
    public MessageStore messageStore;

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

    public void addItem() {
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        item.setName("Test C");

        final MessageTO uncompleted = EASY_RANDOM.nextObject(MessageTO.class);
        uncompleted.setDistances(EASY_RANDOM.objects(DistanceTO.class, 2).collect(Collectors.toList()));
        uncompleted.getDistances().get(0).getSatellite().setName("Test A");
        uncompleted.getDistances().get(1).getSatellite().setName("Test B");
        when(messageStore.findUncompleted()).thenReturn(newArrayList(uncompleted));

        final SatelliteTO satellite = mockGetSatellite();

        mockSaveDistance();

        topSecretService.addItem(item);

        verify(distanceStore).save(eq(new DistanceTO(item.getDistance(), item.getMessage(), satellite, uncompleted)));
    }

    private SatelliteTO mockGetSatellite() {
        final SatelliteTO satellite = EASY_RANDOM.nextObject(SatelliteTO.class);
        when(satelliteStore.getByName(eq("Test C"))).thenReturn(Optional.of(satellite));
        return satellite;
    }

    public void addItemThenNoUncompletedMessages() {
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        item.setName("Test C");

        final SatelliteTO satellite = mockGetSatellite();

        when(messageStore.findUncompleted()).thenReturn(newArrayList());

        final MessageTO uncompleted = mockSaveUncompleted();

        mockSaveDistance();

        topSecretService.addItem(item);

        verify(distanceStore).save(eq(new DistanceTO(item.getDistance(), item.getMessage(), satellite, uncompleted)));
        verify(messageStore).save(any(MessageTO.class));
    }

    private void mockSaveDistance() {
        final DistanceTO saved = EASY_RANDOM.nextObject(DistanceTO.class);
        when(distanceStore.save(any())).thenReturn(saved);
    }

    private MessageTO mockSaveUncompleted() {
        final MessageTO uncompleted = EASY_RANDOM.nextObject(MessageTO.class);
        when(messageStore.save(any())).thenReturn(uncompleted);
        return uncompleted;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addItemThenIsRepeated() {
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        item.setName("Test C");

        mockGetSatellite();

        mockGetUncompletedMessage();

        topSecretService.addItem(item);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addItemThenIsAlreadyThree() {
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        item.setName("Test D");

        mockGetSatellite();

        mockGetUncompletedMessage();

        topSecretService.addItem(item);
    }

    private void mockGetUncompletedMessage() {
        final MessageTO uncompleted = EASY_RANDOM.nextObject(MessageTO.class);
        uncompleted.setDistances(EASY_RANDOM.objects(DistanceTO.class, 3).collect(Collectors.toList()));
        uncompleted.getDistances().get(0).getSatellite().setName("Test A");
        uncompleted.getDistances().get(1).getSatellite().setName("Test B");
        uncompleted.getDistances().get(1).getSatellite().setName("Test C");
        when(messageStore.findUncompleted()).thenReturn(newArrayList(uncompleted));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addItemThenNoItemByName() {
        final TopSecretReqItemTO item = EASY_RANDOM.nextObject(TopSecretReqItemTO.class);
        item.setName("Test C");

        when(satelliteStore.getByName(eq("Test C"))).thenReturn(Optional.empty());

        topSecretService.addItem(item);
    }

}
