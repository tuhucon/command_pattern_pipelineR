package com.example.command_pipeliner;

public class Element2 implements Element {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
