package com.satellite.messenger.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class Mapper {

    private MapperFacade mapperFacade;

    @PostConstruct
    public void init() {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public <I, O> O map(final I input, final Class<O> clazz) {
        return mapperFacade.map(input, clazz);
    }

    public <I, O> List<O> map(final List<I> input, final Class<O> clazz) {
        return input.stream().map(it -> this.map(it, clazz)).collect(toList());
    }

}
