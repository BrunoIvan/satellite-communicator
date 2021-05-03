package com.satellite.messenger.persistence.stores;

import com.satellite.messenger.persistence.repository.SatelliteRepository;
import com.satellite.messenger.pojo.entities.Message;
import com.satellite.messenger.pojo.entities.MessageTO;
import com.satellite.messenger.pojo.entities.Satellite;
import com.satellite.messenger.pojo.entities.SatelliteTO;
import com.satellite.messenger.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.satellite.messenger.pojo.enums.MessageStatusType.UNCOMPLETED;
import static java.util.stream.Collectors.toList;

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

    public Optional<SatelliteTO> getByName(final String name) {
        Specification<Satellite> specification = Specification
                .where((root, cq, cb) -> cb.equal(root.get("name"), name));

        return repository.findAll(specification)
                .stream()
                .map(it -> mapper.map(it, SatelliteTO.class))
                .findFirst();
    }
}
