package com.satellite.messenger.persistence.repository;

import com.satellite.messenger.pojo.entities.Distance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceRepository extends CrudRepository<Distance, Long>, JpaSpecificationExecutor<Distance> {

}