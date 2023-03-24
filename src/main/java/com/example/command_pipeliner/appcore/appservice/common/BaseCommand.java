package com.example.command_pipeliner.appcore.appservice.common;

import an.awesome.pipelinr.Command;

public abstract class BaseCommand<R> implements Command<R> {

    public String getCommandType() {
        return this.getClass().getSimpleName();
    }

    public String getCommandClassName() {
        return this.getClass().getName();
    }
}
