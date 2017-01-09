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

import java.util.concurrent.{CompletableFuture, CompletionStage}
import java.{util => jutil}

import io.rdbc.sapi

import scala.collection.JavaConverters._

private[javawrappers] trait DelegatingBindable[S, J] {

  protected def underlying: sapi.Bindable[S]

  protected def parametrized(underlying: S): J

  def bind(params: jutil.Map[String, Object]): J = {
    parametrized(underlying.bind(params.asScala.toSeq: _*))
  }

  def bindAsync(params: jutil.Map[String, Object]): CompletionStage[J] = {
    CompletableFuture.completedFuture(bind(params))
  }

  def bindByIdx(params: Object*): J = {
    parametrized(underlying.bindByIdx(params))
  }

  def bindByIdxAsync(params: Object*): CompletionStage[J] = {
    CompletableFuture.completedFuture(bindByIdx(params))
  }

  def noParams(): J = {
    parametrized(underlying.noParams)
  }

  def noParamsAsync(): CompletionStage[J] = {
    CompletableFuture.completedFuture(noParams())
  }
}
