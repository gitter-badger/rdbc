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


import java.time.Duration;
import java.util.concurrent.CompletionStage;

/**
 * Represents a parametized update statement.
 * <p>
 * Parametrized statement is a statement that has all parameters provided and is ready to be executed.
 *
 * @define timeoutInfo
 * After the operation takes longer time than `timeout`, operation will be aborted. Note however, that it may not
 * be feasible to abort the operation immediately.
 * @define exceptions
 * Returned future can fail with:
 * - [[io.rdbc.api.exceptions.UnauthorizedException UnauthorizedException]] when client is not authorized to perform the action
 * - [[io.rdbc.api.exceptions.InvalidQueryException InvalidQueryException]] when query is rejected by a database engine as invalid
 * - [[io.rdbc.api.exceptions.InactiveTxException InactiveTxException]] when transaction is in progress but is in inactive state
 * - [[io.rdbc.api.exceptions.ConstraintViolationException ConstraintViolationException]] when operation results in an integrity constraint violation
 * - [[io.rdbc.api.exceptions.UncategorizedRdbcException UncategorizedRdbcException]] when a general statement execution error occurs
 */
public interface ParametrizedUpdate {

    /**
     * Executes this update statement ignoring any resulting information.
     * <p>
     * $timeoutInfo
     * $exceptions
     */
    CompletionStage<Void> execute(Duration timeout);

    /**
     * Executes this update statement returning a number of rows that were deleted.
     * <p>
     * $timeoutInfo
     * $exceptions
     */
    CompletionStage<Long> executeForRowsAffected(Duration timeout);

}
