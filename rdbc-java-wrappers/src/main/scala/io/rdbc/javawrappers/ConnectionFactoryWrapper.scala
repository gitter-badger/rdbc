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

import io.rdbc.japi.util.ThrowingFunction
import io.rdbc.javawrappers.Conversions._
import io.rdbc.{japi, sapi}

import scala.compat.java8.FutureConverters._
import scala.concurrent.ExecutionContext

class ConnectionFactoryWrapper(underlying: sapi.ConnectionFactory)
                              (implicit ec: ExecutionContext)
  extends japi.ConnectionFactory {

  def getConnection(): CompletionStage[japi.Connection] = {
    underlying.connection().map(_.asJava).toJava
  }

  def withConnection[T](body: ThrowingFunction[japi.Connection, CompletionStage[T]]): CompletionStage[T] = {
    underlying.withConnection { sapiConn =>
      body.apply(sapiConn.asJava).toScala
    }.toJava
  }

  def withTransaction[T](body: ThrowingFunction[japi.Connection, CompletionStage[T]]): CompletionStage[T] = {
    underlying.withTransaction { sapiConn =>
      body.apply(sapiConn.asJava).toScala
    }(sapi.Timeout.Inf).toJava
  }

  def shutdown(): CompletionStage[Void] = {
    underlying.shutdown().map[Void](_ => null).toJava
  }
}
