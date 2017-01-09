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

import java.math.{BigDecimal => JBigDec}
import java.time._
import java.util._

import io.rdbc.japi.{Row, SqlNumeric}
import io.rdbc.sapi
import io.rdbc.javawrappers.Conversions._

import scala.compat.java8.OptionConverters._
import scala.reflect.ClassTag

/** Represents a row of a result returned by a database engine.
  *
  * This class defines a set of methods that can be used to get values from the
  * row either by a column name or by a column index. Each method has a version
  * returning an [[scala.Option Option]] to allow null-safe handling of SQL
  * `null` values.
  *
  * @groupname generic Generic getters
  * @groupprio generic 10
  * @groupdesc generic Methods in this group can be used for fetching values
  *            of types not supported by rdbc API out of the box.
  * @groupname unb Unbounded number getters
  * @groupprio unb 20
  * @groupname bool Bool getters
  * @groupprio bool 30
  * @groupname binary Binary getters
  * @groupprio binary 40
  * @groupname char Char getters
  * @groupprio char 50
  * @groupname float Floating point number getters
  * @groupprio float 60
  * @groupname int Integral number getters
  * @groupprio int 70
  * @groupname date Date/time getters
  * @groupprio date 80
  * @groupname string String getters
  * @groupprio string 90
  * @groupname uuid UUID getters
  * @groupprio uuid 100
  * @define exceptions
  *  Throws:
  *  - [[io.rdbc.api.exceptions.ConversionException ConversionException]]
  *  when database value could not be converted to the desired type
  * @define nullSafetyNote
  *  For SQL `null` values, `null` is returned. For null-safety consider using
  *  corresponding `*Opt` method.
  * @define returningNone
  *  For SQL `null` values [[scala.None None]] is returned.
  * @define boolValues
  *  - A single 'T', 'Y' or '1' character values or `1` numeric value are
  *  considered `true`.
  *  - A single 'F', 'N' or '0' character values or `0` numeric value are
  *  considered `false`.
  */
private[javawrappers] class RowWrapper(underlying: sapi.Row) extends Row {

  /** Returns an object of type `A` from column with a given index.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group generic
    * @usecase def col[A](idx: Int): A
    * @inheritdoc
    */
  def getCol[A](idx: Int, cls: Class[A]): A = {
    implicit val clsTag = ClassTag(cls)
    underlying.col(idx)
  }

  /** Returns an object of type `A` from column with a given index.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group generic
    * @usecase def colOpt[A](idx: Int): Option[A]
    * @inheritdoc
    */
  def getColOpt[A](idx: Int, cls: Class[A]): Optional[A] = {
    implicit val clsTag = ClassTag(cls)
    underlying.colOpt(idx).asJava
  }

  private def getColOptScala[A](idx: Int, cls: Class[A]): Option[A] = {
    implicit val clsTag = ClassTag(cls)
    underlying.colOpt(idx)
  }

  /** Returns an object of type `A` from column with a given name.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group generic
    * @usecase def col[A](name: String): A
    * @inheritdoc
    */
  def getCol[A](name: String, cls: Class[A]): A = {
    implicit val clsTag = ClassTag(cls)
    underlying.col(name)
  }

  /** Returns an object of type `A` from column with a given name.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group generic
    * @usecase def colOpt[A](name: String): Option[A]
    * @inheritdoc
    */
  def getColOpt[A](name: String, cls: Class[A]): Optional[A] = {
    implicit val clsTag = ClassTag(cls)
    underlying.colOpt(name).asJava
  }

  private def getColOptScala[A](name: String, cls: Class[A]): Option[A] = {
    implicit val clsTag = ClassTag(cls)
    underlying.colOpt(name)
  }

  /** Returns a `String` from column with a given name.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group string
    */
  def getStr(name: String): String = {
    getCol(name, classOf[String])
  }

  /** Returns a `String` from column with a given name.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group string
    */
  def getStrOpt(name: String): Optional[String] = {
    getColOptScala(name, classOf[String]).asJava
  }

  /** Returns a `String` from column with a given index.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group string
    */
  def getStr(idx: Int): String = {
    getCol(idx, classOf[String])
  }

  /** Returns a `String` from column with a given index.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group string
    */
  def getStrOpt(idx: Int): Optional[String] = {
    getColOptScala(idx, classOf[String]).asJava
  }

  /** Returns a boolean value from column with a given name.
    *
    * $boolValues
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group bool
    */
  def getBool(name: String): java.lang.Boolean = {
    getCol(name, classOf[java.lang.Boolean])
  }

  /** Returns a boolean value from column with a given name.
    *
    * $boolValues
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group bool
    */
  def getBoolOpt(name: String): Optional[java.lang.Boolean] = {
    getColOptScala(name, classOf[java.lang.Boolean]).asJava
  }

  /** Returns a boolean value from column with a given index.
    *
    * $boolValues
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group bool
    */
  def getBool(idx: Int): java.lang.Boolean = {
    getCol(idx, classOf[java.lang.Boolean])
  }

  /** Returns a boolean value from column with a given index.
    *
    * $boolValues
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group bool
    */
  def getBoolOpt(idx: Int): Optional[java.lang.Boolean] = {
    getColOptScala(idx, classOf[java.lang.Boolean]).asJava
  }

  /** Returns a character from column with a given name.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group char
    */
  def getChar(name: String): java.lang.Character = {
    getCol(name, classOf[java.lang.Character])
  }

  /** Returns a character from column with a given name.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group char
    */
  def getCharOpt(name: String): Optional[java.lang.Character] = {
    getColOptScala(name, classOf[java.lang.Character]).asJava
  }

  /** Returns a character from column with a given index.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group char
    */
  def getChar(idx: Int): java.lang.Character = {
    getCol(idx, classOf[java.lang.Character])
  }

  /** Returns a character from column with a given index.
    *
    * Varchar types with a single character are convertible to a `Char`.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group char
    */
  def getCharOpt(idx: Int): Optional[java.lang.Character] = {
    getColOptScala(idx, classOf[java.lang.Character]).asJava
  }

  /** Returns a `Short` from column with a given name.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group int
    */
  def getShort(name: String): java.lang.Short = {
    getCol(name, classOf[java.lang.Short])
  }

  /** Returns a `Short` from column with a given name.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getShortOpt(name: String): Optional[java.lang.Short] = {
    getColOptScala(name, classOf[java.lang.Short]).asJava
  }

  /** Returns a `Short` from column with a given index.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group int
    */
  def getShort(idx: Int): java.lang.Short = {
    getCol(idx, classOf[java.lang.Short])
  }

  /** Returns a `Short` from column with a given index.
    *
    * All numeric types can be converted to `Short`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getShortOpt(idx: Int): Optional[java.lang.Short] = {
    getColOptScala(idx, classOf[java.lang.Short]).asJava
  }

  /** Returns an `Int` from column with a given name.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group int
    */
  def getInt(name: String): java.lang.Integer = {
    getCol(name, classOf[java.lang.Integer])
  }

  /** Returns an `Int` from column with a given name.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getIntOpt(name: String): OptionalInt = {
    getColOptScala(name, classOf[Int]).asPrimitive
  }

  /** Returns an `Int` from column with a given index.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getInt(idx: Int): java.lang.Integer = {
    getCol(idx, classOf[java.lang.Integer])
  }

  /** Returns an `Int` from column with a given index.
    *
    * All numeric types can be converted to `Int`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getIntOpt(idx: Int): OptionalInt = {
    getColOptScala(idx, classOf[Int]).asPrimitive
  }

  /** Returns a `Long` from column with a given name.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group int
    */
  def getLong(name: String): java.lang.Long = {
    getCol(name, classOf[java.lang.Long])
  }

  /** Returns a `Long` from column with a given name.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getLongOpt(name: String): OptionalLong = {
    getColOptScala(name, classOf[Long]).asPrimitive
  }

  /** Returns a `Long` from column with a given index.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group int
    */
  def getLong(idx: Int): java.lang.Long = {
    getCol(idx, classOf[java.lang.Long])
  }

  /** Returns a `Long` from column with a given index.
    *
    * All numeric types can be converted to `Long`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group int
    */
  def getLongOpt(idx: Int): OptionalLong = {
    getColOptScala(idx, classOf[Long]).asPrimitive
  }

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given name.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `numeric` method instead.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group unb
    */
  def getBigDecimal(name: String): JBigDec = {
    getCol(name, classOf[BigDecimal]).bigDecimal
  }

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given name.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `numeric` method instead.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group unb
    */
  def getBigDecimalOpt(name: String): Optional[JBigDec] = {
    getColOptScala(name, classOf[BigDecimal]).map(_.bigDecimal).asJava
  }

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given index.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `numeric` method instead.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group unb
    */
  def getBigDecimal(idx: Int): JBigDec = {
    getCol(idx, classOf[BigDecimal]).bigDecimal
  }

  /** Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given index.
    *
    * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
    * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
    * to be NaN use `numeric` method instead.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group unb
    */
  def getBigDecimalOpt(idx: Int): Optional[JBigDec] = {
    getColOptScala(idx, classOf[BigDecimal]).map(_.bigDecimal).asJava
  }

  /** Returns a [[SqlNumeric]] from column with a given name.
    *
    * All numeric types can be converted to [[SqlNumeric]].
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group unb
    */
  def getNumeric(name: String): SqlNumeric = {
    getCol(name, classOf[sapi.SqlNumeric]).asJava
  }

  /** Returns a [[SqlNumeric]] from column with a given name.
    *
    * All numeric types can be converted to [[SqlNumeric]].
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group unb
    */
  def getNumericOpt(name: String): Optional[SqlNumeric] = {
    getColOptScala(name, classOf[sapi.SqlNumeric]).map(_.asJava).asJava
  }

  /** Returns a [[SqlNumeric]] from column with a given index.
    *
    * All numeric types can be converted to [[SqlNumeric]].
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group unb
    */
  def getNumeric(idx: Int): SqlNumeric = {
    getCol(idx, classOf[sapi.SqlNumeric]).asJava
  }

  /** Returns a [[SqlNumeric]] from column with a given index.
    *
    * All numeric types can be converted to [[SqlNumeric]].
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group unb
    */
  def getNumericOpt(idx: Int): Optional[SqlNumeric] = {
    getColOptScala(idx, classOf[sapi.SqlNumeric]).map(_.asJava).asJava
  }

  /** Returns a `Double` from column with a given name.
    *
    * All numeric types can be converted to `Double`, but some conversions
    * may involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group float
    */
  def getDouble(name: String): java.lang.Double = {
    getCol(name, classOf[java.lang.Double])
  }

  /** Returns a `Double` from column with a given name.
    *
    * All numeric types can be converted to `Double`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group float
    */
  def getDoubleOpt(name: String): OptionalDouble = {
    getColOptScala(name, classOf[Double]).asPrimitive
  }

  /** Returns a `Double` from column with a given index.
    *
    * All numeric types can be converted to `Double`, but some conversions
    * may involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group float
    */
  def getDouble(idx: Int): java.lang.Double = {
    getCol(idx, classOf[java.lang.Double])
  }

  /** Returns a `Double` from column with a given index.
    *
    * All numeric types can be converted to `Double`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group float
    */
  def getDoubleOpt(idx: Int): OptionalDouble = {
    getColOptScala(idx, classOf[Double]).asPrimitive
  }

  /** Returns a `Float` from column with a given name.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group float
    */
  def getFloat(name: String): java.lang.Float = {
    getCol(name, classOf[java.lang.Float])
  }

  /** Returns a `Float` from column with a given name.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group float
    */
  def getFloatOpt(name: String): Optional[java.lang.Float] = {
    getColOptScala(name, classOf[java.lang.Float]).asJava
  }

  /** Returns a `Float` from column with a given index.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group float
    */
  def getFloat(idx: Int): java.lang.Float = {
    getCol(idx, classOf[java.lang.Float])
  }

  /** Returns a `Float` from column with a given index.
    *
    * All numeric types can be converted to `Float`, but some conversions may
    * involve rounding or truncation.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group float
    */
  def getFloatOpt(idx: Int): Optional[java.lang.Float] = {
    getColOptScala(idx, classOf[java.lang.Float]).asJava
  }

  /** Returns an `Instant` from column with a given name.
    *
    * Note that regular timestamp values are not convertible to an `Instant`
    * because timestamp values do not hold a time zone information.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getInstant(name: String): Instant = {
    getCol(name, classOf[Instant])
  }

  /** Returns an `Instant` from column with a given name.
    *
    * Note that regular timestamp values are not convertible to an `Instant`
    * because timestamp values do not hold a time zone information.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getInstantOpt(name: String): Optional[Instant] = {
    getColOptScala(name, classOf[Instant]).asJava
  }

  /** Returns an `Instant` from column with a given index.
    *
    * Note that regular timestamp values are not convertible to an `Instant`
    * because timestamp values do not hold a time zone information.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getInstant(idx: Int): Instant = {
    getCol(idx, classOf[Instant])
  }

  /** Returns an `Instant` from column with a given index.
    *
    * Note that regular timestamp values are not convertible to an `Instant`
    * because timestamp values do not hold a time zone information.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getInstantOpt(idx: Int): Optional[Instant] = {
    getColOptScala(idx, classOf[Instant]).asJava
  }

  /** Returns a `LocalDateTime` from column with a given name.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDateTime(name: String): LocalDateTime = {
    getCol(name, classOf[LocalDateTime])
  }

  /** Returns a `LocalDateTime` from column with a given name.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDateTimeOpt(name: String): Optional[LocalDateTime] = {
    getColOptScala(name, classOf[LocalDateTime]).asJava
  }

  /** Returns a `LocalDateTime` from column with a given index.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDateTime(idx: Int): LocalDateTime = {
    getCol(idx, classOf[LocalDateTime])
  }

  /** Returns a `LocalDateTime` from column with a given index.
    *
    * For SQL date type that does not hold a time, `LocalDateTime` at start
    * of day is returned.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDateTimeOpt(idx: Int): Optional[LocalDateTime] = {
    getColOptScala(idx, classOf[LocalDateTime]).asJava
  }

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDate(name: String): LocalDate = {
    getCol(name, classOf[LocalDate])
  }

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDateOpt(name: String): Optional[LocalDate] = {
    getColOptScala(name, classOf[LocalDate]).asJava
  }

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDate(idx: Int): LocalDate = {
    getCol(idx, classOf[LocalDate])
  }

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalDate` - a time part is truncated.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalDateOpt(idx: Int): Optional[LocalDate] = {
    getColOptScala(idx, classOf[LocalDate]).asJava
  }

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalTime(name: String): LocalTime = {
    getCol(name, classOf[LocalTime])
  }

  /** Returns a `LocalDate` from column with a given name.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalTimeOpt(name: String): Optional[LocalTime] = {
    getColOptScala(name, classOf[LocalTime]).asJava
  }

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalTime(idx: Int): LocalTime = {
    getCol(idx, classOf[LocalTime])
  }

  /** Returns a `LocalDate` from column with a given index.
    *
    * SQL types that represent a date with a time are convertible to
    * `LocalTime` - a date part is truncated.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group date
    */
  def getLocalTimeOpt(idx: Int): Optional[LocalTime] = {
    getColOptScala(idx, classOf[LocalTime]).asJava
  }

  /** Returns a byte array from column with a given name.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used only to fetch binary data.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group binary
    */
  def getBytes(name: String): Array[Byte] = {
    getCol(name, classOf[Array[Byte]])
  }

  /** Returns a byte array from column with a given name.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used
    * only to fetch binary data.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group binary
    */
  def getBytesOpt(name: String): Optional[Array[Byte]] = {
    getColOptScala(name, classOf[Array[Byte]]).asJava
  }

  /** Returns a byte array from column with a given index.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used only to fetch binary data.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group binary
    */
  def getBytes(idx: Int): Array[Byte] = {
    getCol(idx, classOf[Array[Byte]])
  }

  /** Returns a byte array from column with a given index.
    *
    * Note that this method cannot be used to fetch raw value of any type from
    * the database, it should be used
    * only to fetch binary data.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group binary
    */
  def getBytesOpt(idx: Int): Optional[Array[Byte]] = {
    getColOptScala(idx, classOf[Array[Byte]]).asJava
  }

  /** Returns an `UUID` from column with a given name.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group uuid
    */
  def getUuid(name: String): UUID = {
    getCol(name, classOf[UUID])
  }

  /** Returns an `UUID` from column with a given name.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group uuid
    */
  def getUuidOpt(name: String): Optional[UUID] = {
    getColOptScala(name, classOf[UUID]).asJava
  }

  /** Returns an `UUID` from column with a given index.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $nullSafetyNote
    *
    * $exceptions
    *
    * @group uuid
    */
  def getUuid(idx: Int): UUID = {
    getCol(idx, classOf[UUID])
  }

  /** Returns an `UUID` from column with a given index.
    *
    * A string type with a standard UUID representation as defined by
    * `UUID.fromString` is convertible to UUID.
    *
    * $returningNone
    *
    * $exceptions
    *
    * @group uuid
    */
  def getUuidOpt(idx: Int): Optional[UUID] = {
    getColOptScala(idx, classOf[UUID]).asJava
  }
}
