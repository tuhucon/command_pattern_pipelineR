package com.example.command_pipeliner;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class VisitorDefaultTest {

    @Test
    public void testVisitor() {
        List<Element> elements = new ArrayList<>();
        Element element1 = new Element1();
        Element element2 = new Element2();
        Element element3 = new Element3();
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);

        Visitor visitorDefault = new VisitorDefault();
        elements.forEach(e -> e.accept(visitorDefault));

        Visitor visitorChild1 = new VisitorChild1();
        elements.forEach(e -> e.accept(visitorChild1));
    }

}
