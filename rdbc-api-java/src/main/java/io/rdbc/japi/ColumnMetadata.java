package io.rdbc.japi;


import java.util.Optional;

/*
 * Represents metadata of a database column.
 *
 * @param name     column name
 * @param dbTypeId database vendor identifier of a datatype declared for the column
 * @param cls      JVM class that a database driver uses to represent values of the column
 */
//case class ColumnMetadata(name: String, dbTypeId: String, cls: Option[Class[_]])

public final class ColumnMetadata {

    private final String name;
    private final String dbTypeId;
    private final Optional<Class<?>> cls;

    public ColumnMetadata(String name, String dbTypeId, Optional<Class<?>> cls) {
        this.name = name;
        this.dbTypeId = dbTypeId;
        this.cls = cls;
    }

    public static ColumnMetadata of(String name, String dbTypeId) {
        return new ColumnMetadata(name, dbTypeId, Optional.empty());
    }

    public static ColumnMetadata of(String name, String dbTypeId, Class<?> cls) {
        return new ColumnMetadata(name, dbTypeId, Optional.of(cls));
    }

    public String getName() {
        return name;
    }

    public String getDbTypeId() {
        return dbTypeId;
    }

    public Optional<Class<?>> getCls() {
        return cls;
    }
}
