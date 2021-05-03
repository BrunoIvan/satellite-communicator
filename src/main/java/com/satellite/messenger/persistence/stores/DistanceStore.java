package com.satellite.messenger.persistence.stores;

import com.satellite.messenger.persistence.repository.DistanceRepository;
import com.satellite.messenger.pojo.entities.Distance;
import com.satellite.messenger.pojo.entities.DistanceTO;
import com.satellite.messenger.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistanceStore {

    @Autowired
    private DistanceRepository repository;

    @Autowired
    private Mapper mapper;

    public DistanceTO save(final DistanceTO distance) {
        final Distance saved = repository.save(mapper.map(distance, Distance.class));
        return mapper.map(saved, DistanceTO.class);
    }

}
