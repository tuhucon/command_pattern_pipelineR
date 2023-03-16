package com.example.command_pipeliner.common;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class IdempotentCommand<R> extends BaseCommand<R> {

    @Getter
    private final String commandId;

    @Getter
    private long timestamp = System.currentTimeMillis();
}
