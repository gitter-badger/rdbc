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

import java.util.concurrent.CompletionStage
import java.{util => jutil}

import io.rdbc.japi.{AnyParametrizedStatement, AnyStatement}
import io.rdbc.{javawrappers, sapi}
import org.reactivestreams.Publisher

import scala.compat.java8.FutureConverters._
import scala.concurrent.ExecutionContext

private[javawrappers] class AnyStatementWrapper(protected val underlying: sapi.AnyStatement)(implicit ec: ExecutionContext)
  extends AnyStatement
    with DelegatingBindable[sapi.AnyParametrizedStatement, AnyParametrizedStatement] {

  def streamParams(paramsPublisher: Publisher[jutil.Map[String, Object]]): CompletionStage[Void] = {
    ??? //TODO
  }

  def deallocate(): CompletionStage[Void] = {
    underlying.deallocate().map[Void](_ => null).toJava
  }

  protected def parametrized(underlyingParametrized: sapi.AnyParametrizedStatement): javawrappers.AnyParametrizedStatementWrapper = {
    new javawrappers.AnyParametrizedStatementWrapper(underlyingParametrized)
  }
}
