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

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.CompletionStage;


/**
 * Represents a statement result that allows rows streaming.
 *
 * @define futureCompleteNote
 * Note that resulting future may not complete until a `rows` Publisher is
 * complete. To complete this future, clients must either read the rows stream
 * until it is complete or cancel the subscription.
 */
public interface ResultStream {

    /**
     * A number of rows that were affected by the statement.
     * <p>
     * Clients can safely assume that a database is ready to accept new queries
     * after this future completes.
     * <p>
     * $futureCompleteNote
     */
    CompletionStage<Long> getRowsAffected();

    /**
     * A sequence of warnings that were emitted during processing the statement.
     * <p>
     * Clients can safely assume that a database is ready to accept new
     * queries after this future completes.
     * <p>
     * $futureCompleteNote
     */
    CompletionStage<? extends List<Warning>> getWarnings();

    /**
     * A meta data of columns of this result set
     */
    RowMetadata getMetadata();

    /**
     * A reactive streams specification's `Publisher` giving access to the rows.
     * <p>
     * When this publisher signals that it is complete clients can safely assume
     * that a database is ready to accept new queries. If subscription is
     * cancelled, however, clients have to wait for [[Connection.watchForIdle]]
     * future to complete before issuing another query.
     */
    Publisher<Row> getRows();
}
