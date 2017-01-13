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

package io.rdbc.sapi

import scala.annotation.implicitNotFound
import scala.concurrent.duration.Duration

/** Represents a timeout */
@implicitNotFound(
  """Cannot find an implicit Timeout. You might pass
an (implicit timeout: Timeout) parameter to your method
or import io.rdb.sapi.Timeout.Implicits.inf.""")
final case class Timeout(value: Duration) extends AnyVal

/** Timeout companion */
object Timeout {

  private[sapi] trait ImplicitsTrait {
    /** [[scala.concurrent.duration.Duration Duration]] to [[Timeout]] converter */
    implicit class Duration2Timeout(underlying: Duration) {
      def timeout: Timeout = Timeout(underlying)
    }
  }

  val Inf = Timeout(Duration.Inf)

  object Implicits extends ImplicitsTrait {

    /** Infinite timeout (i.e. no timeout) */
    implicit val inf = Inf
  }
}
