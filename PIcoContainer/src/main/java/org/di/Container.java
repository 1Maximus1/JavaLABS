package org.di;

public interface Container {
    <T> T getComponent(Class<T> var1);
}
