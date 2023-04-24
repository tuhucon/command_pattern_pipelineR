package com.example.command_pipeliner;

public interface Visitor {

    void visit(Element2 element2);

    void visit(Element1 element1);

    void visit(Element element);

}
