package com.example.command_pipeliner.appcore.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CommandLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String commandId;

    String request;

    String response;

    String responseClass;

    String commandClass;

    String commandType;

    @Enumerated(EnumType.STRING)
    CommandStatus status;
}
