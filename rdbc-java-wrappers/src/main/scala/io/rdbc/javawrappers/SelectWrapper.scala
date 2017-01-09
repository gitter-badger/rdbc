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

import io.rdbc.japi.{ParametrizedSelect, Select}
import io.rdbc.{javawrappers, sapi}

import scala.concurrent.ExecutionContext

/** Represents a select statement */
private[javawrappers] class SelectWrapper(protected val underlying: sapi.Select)(implicit ec: ExecutionContext)
  extends Select
    with DelegatingBindable[sapi.ParametrizedSelect, ParametrizedSelect] {

  protected def parametrized(underlying: sapi.ParametrizedSelect): ParametrizedSelect = {
    new javawrappers.ParametrizedSelectWrapper(underlying)
  }
}
