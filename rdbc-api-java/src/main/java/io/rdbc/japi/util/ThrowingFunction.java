package io.rdbc.japi.util;

@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R apply(T t) throws Throwable;
}
