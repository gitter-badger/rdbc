/*
 * Copyright 2016-2017 Krzysztof Pado
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

/** Represents a converter of values returned by database to type desired by
  * a client.
  *
  * @tparam T conversion's target type
  */
trait TypeConverter[T] {

  /** A conversion's target class */
  def cls: Class[T]

  /** Attempts to convert any value to the target type
    *
    * Throws:
    *  - [[io.rdbc.api.exceptions.ConversionException ConversionException]]
    * when conversion to desired type is not possible
    */
  def fromAny(any: Any): T
}
