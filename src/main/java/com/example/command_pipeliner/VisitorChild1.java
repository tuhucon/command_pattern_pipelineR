package com.example.command_pipeliner;

public class VisitorChild1 implements Visitor {

    @Override
    public void visit(Element1 element1) {
        System.out.println("Visitor Child visit Element1: " + element1.getClass().getName());
    }

    @Override
    public void visit(Element2 element2) {
        System.out.println("Visitor Child visit Element2: " + element2.getClass().getName());
    }

    @Override
    public void visit(Element element) {
        System.out.println("Visitor Child visit Element: " + element.getClass().getName());
    }
}
