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

import java.lang.Long
import java.time.Duration
import java.util.UUID
import java.util.concurrent.CompletionStage

import io.rdbc.japi.{ParametrizedReturningInsert, ResultSet, ResultStream}
import io.rdbc.sapi

import scala.concurrent.ExecutionContext

private[javawrappers] class ParametrizedReturningInsertWrapper(underlying: sapi.ParametrizedReturningInsert)(implicit ec: ExecutionContext)
  extends ParametrizedReturningInsert {

  def executeForKeysStream(timeout: Duration): CompletionStage[ResultStream] = ???

  def executeForKeysSet(timeout: Duration): CompletionStage[ResultSet] = ???

  def executeForKey[K](cls: Class[K], timeout: Duration): CompletionStage[K] = ???

  def executeForIntKey(timeout: Duration): CompletionStage[Integer] = ???

  def executeForLongKey(timeout: Duration): CompletionStage[Long] = ???

  def executeForUUIDKey(timeout: Duration): CompletionStage[UUID] = ???

  def execute(timeout: Duration): CompletionStage[Void] = ???

  def executeForRowsAffected(timeout: Duration): CompletionStage[Long] = ???
}
