package com.example.command_pipeliner.common;

import an.awesome.pipelinr.Command;

public abstract class BaseCommand<R> implements Command<R> {

    public String getCommandType() {
        return this.getClass().getSimpleName();
    }
}
