package com.example.command_pipeliner.appcore.domain.model.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DomainEvent {

    Long id;

    Integer version;

    public String getEventType() {
        return this.getClass().getSimpleName();
    }

    public String getEventClassName() {
        return this.getClass().getName();
    }
}
