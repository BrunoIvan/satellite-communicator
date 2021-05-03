package com.satellite.messenger.persistence.repository;

import com.satellite.messenger.pojo.entities.Message;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>, JpaSpecificationExecutor<Message> {

}