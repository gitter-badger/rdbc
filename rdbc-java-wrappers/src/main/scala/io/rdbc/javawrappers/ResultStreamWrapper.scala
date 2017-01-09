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

import io.rdbc._
import io.rdbc.japi.{ResultStream, Row, RowMetadata, Warning}
import io.rdbc.javawrappers.Conversions._
import org.reactivestreams.Publisher

import scala.collection.JavaConverters._
import scala.compat.java8.FutureConverters._
import scala.concurrent.ExecutionContext

private[javawrappers] class ResultStreamWrapper(underlying: sapi.ResultStream)
                                               (implicit ec: ExecutionContext)
  extends ResultStream {

  def getRowsAffected(): CompletionStage[java.lang.Long] = {
    underlying.rowsAffected.map[java.lang.Long](identity(_)).toJava
  }

  def getWarnings(): CompletionStage[_ <: jutil.List[Warning]] = {
    underlying.warnings.map(seq => new jutil.ArrayList(seq.map(_.asJava).asJava)).toJava
  }

  def getMetadata(): RowMetadata = {
    underlying.metadata.asJava
  }

  def getRows(): Publisher[Row] = {
    ??? //TODO
  }
}
