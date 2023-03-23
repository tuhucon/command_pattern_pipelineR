package com.example.command_pipeliner.appcore.domain.model.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DomainEvent {

    Integer version;
}
