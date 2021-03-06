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

package io.rdbc.typeconv

import io.rdbc.api.exceptions.ConversionException
import io.rdbc.sapi.TypeConverter

object CharConverter extends TypeConverter[Char] {
  val cls = classOf[Char]

  override def fromAny(any: Any): Char = any match {
    case char: Char => char

    case str: String =>
      if (str.length == 1) str.head
      else throw new ConversionException(str, classOf[Char])

    case _ => throw new ConversionException(any, classOf[Char])
  }
}
