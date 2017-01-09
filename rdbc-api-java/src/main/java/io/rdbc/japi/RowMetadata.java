package io.rdbc.japi;

import java.util.ArrayList;
import java.util.List;

public final class RowMetadata {

    private final List<ColumnMetadata> columns;

    RowMetadata(List<ColumnMetadata> columns) {
        this.columns = new ArrayList<>(columns);
    }

    public static RowMetadata of(List<ColumnMetadata> columns) {
        return new RowMetadata(columns);
    }

    public List<ColumnMetadata> getColumns() {
        return new ArrayList<>(columns);
    }
}
