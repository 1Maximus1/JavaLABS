package com.di.implementation;
import org.di.Binder;
import org.di.Configuration;
import org.di.Container;
import org.di.Environment;

import java.util.HashMap;
import java.util.Map;

public class BasicEnvironment implements Environment {
    public BasicEnvironment() {
    }

    public Container configure(Configuration configuration) {
        Map<Class<?>, Class<?>> classesMap = new HashMap();
        Map<Class<?>, Object> instancesMap = new HashMap();
        Binder binder = new BasicBinder(classesMap, instancesMap);
        Container container = new BasicContainer(classesMap, instancesMap);
        configuration.configure(binder);
        return container;
    }
}
