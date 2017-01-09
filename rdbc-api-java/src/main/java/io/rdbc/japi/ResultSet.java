/*
 * Copyright 2016 Krzysztof Pado
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rdbc.japi;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Represents a set of rows returned by a database engine.
 *
 * @param rowsAffected a number of rows that were affected by the statement
 *                     that this result set is for
 * @param warnings     a sequence of warnings that were emitted by the database
 *                     during processing the statement that this result set is for
 * @param metadata     a meta data of columns of this result set
 * @param rows         a sequence of rows returned by a database
 */
public final class ResultSet implements Iterable<Row> {

    private final Long rowsAffected;
    private final List<Warning> warnings;
    private final RowMetadata metadata;
    private final List<Row> rows;

    public ResultSet(Long rowsAffected, List<Warning> warnings,
              RowMetadata metadata, List<Row> rows) {
        this.rowsAffected = rowsAffected;
        this.warnings = new ArrayList<>(warnings);
        this.metadata = metadata;
        this.rows = new ArrayList<>(rows);
    }

    public static ResultSet of(Long rowsAffected, List<Warning> warnings,
                               RowMetadata metadata, List<Row> rows) {
        return new ResultSet(
                rowsAffected,
                warnings,
                metadata,
                rows
        );
    }

    public Long getRowsAffected() {
        return rowsAffected;
    }

    public List<Warning> getWarnings() {
        return new ArrayList<>(warnings);
    }

    public RowMetadata getMetadata() {
        return this.metadata;
    }

    public List<Row> getRows() {
        return new ArrayList<>(rows);
    }

    @Override
    public Iterator<Row> iterator() {
        return new Iterator<Row>() {
            //this inner class was created to remove "remove()" operation
            Iterator<Row> underlying = rows.iterator();

            @Override
            public boolean hasNext() {
                return underlying.hasNext();
            }

            @Override
            public Row next() {
                return underlying.next();
            }
        };
    }
}
