package com.di.implementation;

import com.di.util.ContainerException;
import com.di.util.UnregisteredComponentException;
import org.di.Container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

public class BasicContainer implements Container {
    private final Map<Class<?>, Class<?>> classesMap;
    private final Map<Class<?>, Object> instancesMap;

    public BasicContainer(Map<Class<?>, Class<?>> classesMap, Map<Class<?>, Object> instancesMap) {
        this.classesMap = classesMap;
        this.instancesMap = instancesMap;
    }

    public <T> T getComponent(Class<T> clazz) {
        if (this.instancesMap.containsKey(clazz)) {
            return (T) this.instancesMap.get(clazz);
        } else if (!this.classesMap.containsKey(clazz)) {
            List<Class<? extends T>> implementations = this.getAllStoredImplementations(clazz);
            if (implementations.isEmpty()) {
                throw new UnregisteredComponentException("Unregistered class: %s".formatted(clazz));
            } else if (implementations.size() > 1) {
                throw new ContainerException("Too many implementations for class %s: %s".formatted(clazz, implementations));
            } else {
                return (T) this.getComponent((Class)implementations.get(0));
            }
        } else {
            Class<T> requestedClass = (Class)this.classesMap.get(clazz);
            return requestedClass.equals(clazz) || !this.classesMap.containsKey(requestedClass) && !this.instancesMap.containsKey(requestedClass) ? this.getInstance(requestedClass) : this.getComponent(requestedClass);
        }
    }

    private <T> T getInstance(Class<T> clazz) {
        T instance = null;

        try {
            instance = this.getInstanceBasedOnInjectConstructor(clazz);
            if (instance == null) {
                instance = this.getInstanceBasedOnEmptyConstructor(clazz);
            }

            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException var4) {
            throw new ContainerException("Couldn't instantiate class %s".formatted(clazz));
        }
    }

    private <T> T getInstanceBasedOnInjectConstructor(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor[] var5;
        int var4 = (var5 = clazz.getDeclaredConstructors()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            Constructor<?> constructor = var5[var3];
            if (constructor.getAnnotation(Inject.class) != null) {
                Class[] params = constructor.getParameterTypes();
                Object[] args = new Object[params.length];

                for(int i = 0; i < params.length; ++i) {
                    args[i] = this.getComponent(params[i]);
                }

                constructor.setAccessible(true);
                T instance = (T) constructor.newInstance(args);
                if (clazz.getAnnotation(Singleton.class) != null) {
                    this.instancesMap.put(clazz, instance);
                }

                return instance;
            }
        }

        return null;
    }

    private <T> T getInstanceBasedOnEmptyConstructor(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        if (clazz.getAnnotation(Singleton.class) != null) {
            this.instancesMap.put(clazz, instance);
        }

        return instance;
    }

    private <T> List<Class<? extends T>> getAllStoredImplementations(Class<T> clazz) {
        List<Class<? extends T>> implementations = new ArrayList();
        Iterator var4 = this.classesMap.keySet().iterator();

        while(var4.hasNext()) {
            Class<?> key = (Class)var4.next();

            for(Class<?> superClass = key; !superClass.equals(Object.class); superClass = superClass.getSuperclass()) {
                if (superClass.equals(clazz)) {
                    implementations.add((Class<? extends T>) key);
                }
            }
        }

        return implementations;
    }
}
