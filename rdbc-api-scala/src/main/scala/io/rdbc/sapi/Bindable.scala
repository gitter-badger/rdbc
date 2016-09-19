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

import scala.concurrent.Future

trait Bindable[T] {
  def bind(params: (String, Any)*): T

  def bindF(params: (String, Any)*): Future[T] = Future.successful(bind(params: _*))

  def bindByIdx(params: Any*): T

  def bindByIdxF(params: Any*): Future[T] = Future.successful(bindByIdx(params: _*))

  def noParams: T
}