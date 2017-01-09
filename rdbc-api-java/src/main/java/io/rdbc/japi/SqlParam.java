package io.rdbc.japi;

import java.util.NoSuchElementException;

public final class SqlParam<T> {

    private final Class<T> type;
    private final T value;

    public static <T> SqlParam<T> sqlNull(Class<T> type) {
        return new SqlParam<>(type, null);
    }

    public static <T> SqlParam<T> of(Class<T> type, T value) {
        return new SqlParam<T>(type, value);
    }

    private SqlParam(Class<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        if (value != null) {
            return value;
        } else {
            throw new NoSuchElementException("SqlParam value is null");
        }
    }
}
