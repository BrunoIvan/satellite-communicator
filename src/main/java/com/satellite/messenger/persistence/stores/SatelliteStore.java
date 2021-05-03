package com.satellite.messenger.persistence.stores;

import com.satellite.messenger.persistence.repository.SatelliteRepository;
import com.satellite.messenger.pojo.entities.Satellite;
import com.satellite.messenger.pojo.entities.SatelliteTO;
import com.satellite.messenger.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Slf4j
public class SatelliteStore {

    @Autowired
    private SatelliteRepository repository;

    @Autowired
    private Mapper mapper;

    public List<SatelliteTO> getAll() {
        final List<Satellite> result = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        log.info("Finding all satellites");
        final List<SatelliteTO> mapped = mapper.map(result, SatelliteTO.class);
        log.info("Finding all satellites. Found {}", mapped.size());
        return mapped;
    }

}
