package org.di;

public interface Binder {
    <T> void bind(Class<T> var1);

    <T> void bind(Class<T> var1, Class<? extends T> var2);

    <T> void bind(Class<T> var1, T var2);
}
