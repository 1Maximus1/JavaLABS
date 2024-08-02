package com.di.implementation;

import com.di.util.BindException;
import com.di.util.CircularInjectException;
import org.di.Binder;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

public class BasicBinder implements Binder {
    private final Map<Class<?>, Class<?>> classesMap;
    private final Map<Class<?>, Object> instancesMap;

    public BasicBinder(Map<Class<?>, Class<?>> classesMap, Map<Class<?>, Object> instancesMap) {
        this.classesMap = classesMap;
        this.instancesMap = instancesMap;
    }

    public <T> void bind(Class<T> clazz) {
        if (clazz == null) {
            throw new BindException("Binding null is not allowed");
        } else {
            this.bind(clazz, clazz);
        }
    }

    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        if (!this.classesMap.containsKey(clazz) && !this.instancesMap.containsKey(clazz)) {
            int implMod = implementation.getModifiers();
            if (!Modifier.isAbstract(implMod) && !Modifier.isInterface(implMod)) {
                int injectAnnotationsCount = this.findAllInjectConstructors(implementation).size();
                if (injectAnnotationsCount > 1) {
                    throw new BindException("Implementation cannot have multiple constructor injection");
                } else {
                    this.checkForCircularInjection(implementation);
                    this.classesMap.put(clazz, implementation);
                }
            } else {
                throw new BindException("Implementation cannot be abstract");
            }
        } else {
            throw new BindException("Rebinding is not allowed: class %s was already bound".formatted(clazz));
        }
    }

    public <T> void bind(Class<T> clazz, T instance) {
        if (!this.classesMap.containsKey(clazz) && !this.instancesMap.containsKey(clazz)) {
            this.instancesMap.put(clazz, instance);
        } else {
            throw new BindException("Rebinding is not allowed: class %s was already bound".formatted(clazz));
        }
    }

    private List<Constructor<?>> findAllInjectConstructors(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).filter((constructor) -> constructor.getAnnotation(Inject.class) != null).toList();
    }

    private Optional<Constructor<?>> findAnyInjectConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).filter((constructor) -> constructor.getAnnotation(Inject.class) != null).findAny();
    }

    private void checkForCircularInjection(Class<?> clazz) {
        Queue<Class<?>> toBeCheckedQueue = new ArrayDeque();
        List<Class<?>> checked = new ArrayList();
        toBeCheckedQueue.add(clazz);

        while(!toBeCheckedQueue.isEmpty()) {
            Class<?> toBeChecked = toBeCheckedQueue.poll();
            Optional<Constructor<?>> constructorOpt = this.findAnyInjectConstructor(toBeChecked);
            if (constructorOpt.isPresent()) {
                Constructor<?> constructor = constructorOpt.get();
                Collections.addAll(toBeCheckedQueue, constructor.getParameterTypes());
                checked.add(toBeChecked);
                if (checked.stream().distinct().count() != (long)checked.size()) {
                    throw new CircularInjectException("There is circular inject in class %s".formatted(clazz));
                }
            }
        }

    }
}

