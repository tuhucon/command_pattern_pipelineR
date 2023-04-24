package com.example.command_pipeliner;

public interface Element {

    void accept(Visitor visitor);

}
