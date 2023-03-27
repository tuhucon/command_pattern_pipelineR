package com.example.command_pipeliner.appcore.domain.model.common;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ApplyEventResolver {

    private static ConcurrentMap<Class, ConcurrentMap<Class, Method>> eventMethods = new ConcurrentHashMap<>();

    private ApplyEventResolver() {

    }

    @SneakyThrows
    public static void applyEvent(Object obj, DomainEvent event) {
        if (obj == null || event == null) {
            throw new RuntimeException("Domain Object and event object must be non null");
        }

        Class<?> objClass = obj.getClass();
        Class<?> eventClass = event.getClass();
        Method invokeMethod = null;

        if (eventMethods.get(objClass) != null && eventMethods.get(objClass).get(eventClass) != null) {
            invokeMethod = eventMethods.get(objClass).get(eventClass);

        } else {
            eventMethods.putIfAbsent(objClass, new ConcurrentHashMap<>());
            for (Method method : objClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(ApplyEvent.class)) {
                    Class<? extends DomainEvent> scanningEventType = method.getDeclaredAnnotation(ApplyEvent.class).eventType();
                    eventMethods.get(objClass).putIfAbsent(scanningEventType, method);
                    if (scanningEventType == eventClass) {
                        invokeMethod = method;
                    }
                }
            }
        }

        if (invokeMethod == null) {
            throw new RuntimeException("cann not find appy method for event: " + eventClass.getName() + " in domain: " + objClass.getName());
        }
        invokeMethod.setAccessible(true);
        invokeMethod.invoke(obj, event);
    }
}
