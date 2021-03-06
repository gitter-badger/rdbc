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
import io.rdbc.sapi
import io.rdbc.sapi.TypeConverter

object BigDecimalConverter extends TypeConverter[BigDecimal] {
  val cls = classOf[BigDecimal]

  override def fromAny(any: Any): BigDecimal = any match {
    case bd: BigDecimal => bd
    case d: Double => BigDecimal(d)
    case f: Float => BigDecimal(f.toDouble)
    case l: Long => BigDecimal(l)
    case i: Int => BigDecimal(i)
    case s: Short => BigDecimal(s.toInt)
    case b: Byte => BigDecimal(b.toInt)
    case sapi.SqlNumeric.Val(bd) => bd

    case str: String =>
      try {
        BigDecimal.exact(str)
      } catch {
        case _: NumberFormatException => throw new ConversionException(any, classOf[BigDecimal])
      }

    case _ => throw new ConversionException(any, classOf[BigDecimal])
  }
}
