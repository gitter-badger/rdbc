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

/** Represents a database connection (session).
  *
  * Instances of implementations of this trait can be obtained using a
  * [[ConnectionFactory]]. When clients are done with the connection, they are
  * required to call a `release` method co clean up resources such as open sockets.
  *
  * Invoking any method of this trait when any previous operation has not
  * completed yet is not allowed. Operation is considered complete when a resulting
  * [[scala.concurrent.Future Future]] completes.
  *
  * Transaction management has to be done using `beginTx`, `commitTx` and
  * `rollbackTx` methods. Using SQL statements to manage transaction state is
  * not allowed.
  *
  * [[SqlWithParams]] instances passed to `Connection`'s methods can be created
  * using `sql` string interpolator, for example:
  * {{{
  *   import io.rdbc.sapi._
  *
  *   val conn: Connection = ???
  *   val login = "jdoe"
  *   conn.select(sql"select * from users where login = $$login").executeForStream()
  * }}}
  *
  * Alternatively, when bare Strings are used as SQL statements, parameters
  * are specified by name. Parameter name is an alphanumeric string starting
  * with a letter, prefixed with a colon. Example:
  *
  * {{{
  *   import io.rdbc.sapi._
  *
  *   val conn: Connection = ???
  *   val login = "jdoe"
  *   for {
  *    select       <- conn.select(sql"select * from users where login = :login")
  *    parametrized <- select.bindF("login" -> login)
  *   } yield parametrized.executeForStream()
  * }}}
  *
  * @groupname tx Transaction management
  * @groupprio tx 10
  * @groupname stmtInter Statement producers (string interpolation)
  * @groupprio stmtInter 20
  * @groupname stmtBare Statement producers (bare strings)
  * @groupprio stmtBare 30
  * @define timeoutInfo
  *  After the operation takes longer time than `timeout`, operation will be
  *  aborted. Note however, that it may not be feasible to abort the operation
  *  immediately.
  * @define statementExceptions
  *  Returned future can fail with:
  *  - [[io.rdbc.api.exceptions.SyntaxErrorException SyntaxErrorException]]
  *  when statement is not syntactically correct
  *  - [[io.rdbc.api.exceptions.UncategorizedRdbcException UncategorizedRdbcException]]
  *  when general error occurs
  * @define timeoutException
  *  - [[io.rdbc.api.exceptions.TimeoutException TimeoutException]]
  *  when maximum operation time has been exceeded
  * @define bindExceptions
  *  - [[io.rdbc.api.exceptions.MissingParamValException MissingParamValException]]
  *  when some parameter value was not provided
  *  - [[io.rdbc.api.exceptions.NoSuitableConverterFoundException NoSuitableConverterFoundException]]
  *  when some parameter value's type is not convertible to a database type
  * @define statementParametrization
  *  For syntax of statement parametrization see a [[Connection]] documentation.
  * @define returningInsert
  *  Returns a [[ReturningInsert]] instance bound to this connection that
  *  represents a SQL insert statement that can return keys generated by the
  *  database engine.
  *
  *  It is not defined whether the method returns exceptionally if SQL provided
  *  is a valid statement, but not an insert statement.
  * @define interpolatorExample
  *  [[SqlWithParams]] parameter instance is meant to be constructed using `sql`
  *  string interpolator, for example:
  *  {{{
  *      import io.rdbc.sapi.Interpolators._
  *      val x = 1
  *      val y = 10
  *      val stmt = conn.select(sql"select * from table where colx > $$x and coly < $$y")
  *  }}}
  */
public interface Connection {

  /** Begins a database transaction.
    *
    * Using this method is a preferred way of starting a transaction, using SQL
    * statements to manage transaction state may lead to undefined behavior.
    *
    * $timeoutInfo
    *
    * Returned future can fail with:
    *  - [[io.rdbc.api.exceptions.BeginTxException BeginTxException]]
    * when general error occurs
    * $timeoutException
    *
    * @group tx
    */
  CompletionStage<Void> beginTx(Duration timeout);

  /** Commits a database transaction.
    *
    * Using this method is a preferred way of committing a transaction, using
    * SQL statements to manage transaction state may lead to undefined behavior.
    *
    * $timeoutInfo
    *
    * Returned future can fail with:
    *  - [[io.rdbc.api.exceptions.BeginTxException CommmitTxException]]
    * when general error occurs
    * $timeoutException
    *
    * @group tx
    */
  CompletionStage<Void> commitTx(Duration timeout);

  /** Rolls back a database transaction.
    *
    * Using this method is a preferred way of rolling back a transaction, using
    * SQL statements to manage transaction state may lead to undefined behavior.
    *
    * $timeoutInfo
    *
    * Returned future can fail with:
    *  - [[io.rdbc.api.exceptions.BeginTxException RollbackTxException]]
    * when general error occurs
    * $timeoutException
    *
    * @group tx
    */
  CompletionStage<Void> rollbackTx(Duration timeout);

  /** Releases the connection and underlying resources.
    *
    * Only idle connections can be released using this method. To forcibly
    * release the connection use [[forceRelease]] method.
    *
    * After calling this method no future operations on the instance are allowed.
    *
    * Returned future can fail with:
    *  - [[io.rdbc.api.exceptions.ConnectionReleaseException ConnectionReleaseException]]
    * when general error occurs
    */
  CompletionStage<Void> release();

  /** Releases the connection and underlying resources regardless of whether
    * the connection is currently in use or not.
    *
    * After calling this method no future operations on the instance are allowed.
    *
    * Returned future can fail with:
    *  - [[io.rdbc.api.exceptions.ConnectionReleaseException ConnectionReleaseException]]
    * when general error occurs
    */
  CompletionStage<Void> forceRelease();

  /** Checks whether the connection is still usable.
    *
    * If checking takes longer than `timeout`, connection is considered unusable.
    *
    * @return Future of `true` iff connection is usable, `false` otherwise
    */
  CompletionStage<Boolean> validate(Duration timeout);

  /** Returns a [[Select]] instance bound to this connection that represents
    * a SQL select statement.
    *
    * It is not defined whether the method returns exceptionally if SQL provided
    * is a valid statement but not a select statement.
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<Select> select(String sql);


  /** Returns an [[Update]] instance bound to this connection that represents
    * a SQL update statement.
    *
    * It is not defined whether the method returns exceptionally if SQL provided
    * is a valid statement, but not an update statement.
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<Update> update(String sql);

  /** Returns an [[Insert]] instance bound to this connection that represents
    * a SQL insert statement.
    *
    * It is not defined whether the method returns exceptionally if SQL provided
    * is a valid statement, but not an insert statement.
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<Insert> insert(String sql);

  /** $returningInsert
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<ReturningInsert> returningInsert(String sql);

  /** $returningInsert
    *
    * `keyColumns` parameter is used to list column names that database engine
    * generates keys for. Only keys from these columns will be returned to the
    * client. This method is a more efficient version of `returningInsert(sql: String)`
    * method that returns all keys generated by the database engine.
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<ReturningInsert> returningInsert(String sql, String... keyColumns);

  /** Returns a [[Delete]] instance bound to this connection that represents
    * a SQL delete statement.
    *
    * It is not defined whether the method returns exceptionally if SQL provided
    * is a valid statement, but not a delete statement.
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<Delete> delete(String sql);

  /** Returns an [[AnyStatement]] instance bound to this connection that
    * represents any SQL statement.
    *
    * Clients are encouraged to use `select`, `insert`, `update`, `delete`
    * methods in favor of this generic method.
    *
    * $statementParametrization
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<AnyStatement> statement(String sql);

  /** Returns a [[DdlStatement]] instance bound to this connection
    * that represents a DDL statement
    *
    * $statementExceptions
    *
    * @group stmtBare
    */
  CompletionStage<DdlStatement> ddl(String sql);

  /** Returns a future that is complete when this connection is idle and ready
    * for accepting queries. */
  CompletionStage<Connection> watchForIdle();
}
