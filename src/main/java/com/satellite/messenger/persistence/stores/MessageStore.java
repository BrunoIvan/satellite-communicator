package com.satellite.messenger.persistence.stores;

import com.satellite.messenger.persistence.repository.MessageRepository;
import com.satellite.messenger.pojo.entities.Message;
import com.satellite.messenger.pojo.entities.MessageTO;
import com.satellite.messenger.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.satellite.messenger.pojo.enums.MessageStatusType.UNCOMPLETED;
import static com.satellite.messenger.pojo.enums.MessageStatusType.UNSOLVED;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class MessageStore {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private Mapper mapper;

    public List<MessageTO> findUncompleted() {
        Specification<Message> specification = Specification
                .where((root, cq, cb) -> cb.equal(root.get("status"), UNCOMPLETED));

        return repository.findAll(specification)
                .stream()
                .map(it -> mapper.map(it, MessageTO.class))
                .collect(toList());
    }

    public MessageTO save(final MessageTO message) {
        final Message saved = repository.save(mapper.map(message, Message.class));
        return mapper.map(saved, MessageTO.class);
    }

    public List<MessageTO> findUnsolved() {
        Specification<Message> specification = Specification
                .where((root, cq, cb) -> cb.equal(root.get("status"), UNSOLVED));

        return repository.findAll(specification)
                .stream()
                .map(it -> mapper.map(it, MessageTO.class))
                .collect(toList());
    }
}
