package com.example.command_pipeliner.appcore.appservice.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class IdempotentCommand<R> extends BaseCommand<R> {

    @Getter
    private final String commandId;

    @Getter
    private long timestamp = System.currentTimeMillis();
}
