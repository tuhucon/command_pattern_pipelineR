package com.example.command_pipeliner;

public class VisitorDefault implements Visitor {

    @Override
    public void visit(Element1 element1) {
        System.out.println("visit element1: " + element1.getClass().getName());
    }

    @Override
    public void visit(Element2 element2) {
        System.out.println("visit element2: " + element2.getClass().getName());
    }

    @Override
    public void visit(Element element) {
       System.out.println("visit element: " + element.getClass().getName());
    }
}
