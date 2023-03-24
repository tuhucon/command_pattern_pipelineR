package com.example.command_pipeliner.appcore.domain.model.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseAggregateRoot {

    @JsonIgnore
    List<DomainEvent> events = new ArrayList<>();

    abstract protected void validate();

    protected void addEvent(DomainEvent event) {
        if (events.size() == 0) {
            event.setVersion(1);
        } else {
            DomainEvent latestEvent = events.get(events.size() -1);
            event.setVersion(latestEvent.getVersion() + 1);
        }
        events.add(event);
    }

    abstract protected void apply(DomainEvent event);

    protected void handle(DomainEvent event) {
        apply(event);
        validate();
        addEvent(event);
    }

    protected void init() {
        getEvents().forEach(this::apply);
    }
}
