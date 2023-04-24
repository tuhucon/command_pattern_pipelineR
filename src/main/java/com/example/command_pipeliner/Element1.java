package com.example.command_pipeliner;

public class Element1 implements Element {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
