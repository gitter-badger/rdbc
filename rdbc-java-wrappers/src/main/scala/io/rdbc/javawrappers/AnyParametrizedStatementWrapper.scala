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

package io.rdbc.javawrappers

import java.time.Duration
import java.util.Optional
import java.util.concurrent.CompletionStage

import io.rdbc.japi.util.ThrowingFunction
import io.rdbc.japi.{AnyParametrizedStatement, ResultSet, ResultStream, Row}
import io.rdbc.javawrappers.Conversions._
import io.rdbc.sapi.Timeout
import io.rdbc.{javawrappers, sapi}

import scala.compat.java8.FutureConverters._
import scala.compat.java8.OptionConverters._
import scala.concurrent.ExecutionContext

private[javawrappers] class AnyParametrizedStatementWrapper(underlying: sapi.AnyParametrizedStatement)
                                     (implicit ec: ExecutionContext)
  extends AnyParametrizedStatement {

  def executeForStream(timeout: Duration): CompletionStage[ResultStream] = {
    underlying.executeForStream()(Timeout(timeout.asScala))
      .map(_.asJava).toJava
  }


  def executeForSet(timeout: Duration): CompletionStage[ResultSet] = {
    underlying.executeForSet()(Timeout(timeout.asScala))
      .map(_.asJava).toJava
  }

  def executeIgnoringResult(timeout: Duration): CompletionStage[Void] = {
    underlying.executeIgnoringResult()(Timeout(timeout.asScala))
      .map[Void](_ => null).toJava
  }


  def executeForRowsAffected(timeout: Duration): CompletionStage[java.lang.Long] = {
    underlying.executeForRowsAffected()(Timeout(timeout.asScala))
      .map[java.lang.Long](identity(_)).toJava
  }

  def executeForFirstRow(timeout: Duration): CompletionStage[Optional[Row]] = {
    underlying.executeForFirstRow()(Timeout(timeout.asScala))
      .map(_.map(_.asJava).asJava).toJava
  }

  def executeForValue[A](valExtractor: ThrowingFunction[Row, A],
                         timeout: Duration): CompletionStage[Optional[A]] = {
    underlying.executeForValue { sapiRow =>
      valExtractor.apply(new javawrappers.RowWrapper(sapiRow))
    }(Timeout(timeout.asScala)).map(_.asJava).toJava
  }

  def executeForValueOpt[A](valExtractor: ThrowingFunction[Row, Optional[A]],
                            timeout: Duration): CompletionStage[Optional[Optional[A]]] = {
    underlying.executeForValueOpt { sapiRow =>
      valExtractor.apply(new javawrappers.RowWrapper(sapiRow)).asScala
    }(Timeout(timeout.asScala)).map(_.map(_.asJava).asJava).toJava
  }

  def deallocate(): CompletionStage[Void] = {
    underlying.deallocate().map[Void](_ => null).toJava
  }
}
