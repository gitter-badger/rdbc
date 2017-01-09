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

package io.rdbc.japi;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Represents a row of a result returned by a database engine.
 * <p>
 * This class defines a set of methods that can be used to get values from the
 * row either by a column name or by a column index. Each method has a version
 * returning an [[scala.Option Option]] to allow null-safe handling of SQL
 * `null` values.
 *
 * @groupname generic Generic getters
 * @groupprio generic 10
 * @groupdesc generic Methods in this group can be used for fetching values
 * of types not supported by rdbc API out of the box.
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
 * @groupname int integral number getters
 * @groupprio int 70
 * @groupname date Date/time getters
 * @groupprio date 80
 * @groupname string String getters
 * @groupprio string 90
 * @groupname uuid UUID getters
 * @groupprio uuid 100
 * @define exceptions
 * Throws:
 * - [[io.rdbc.api.exceptions.ConversionException ConversionException]]
 * when database value could not be converted to the desired type
 * @define nullSafetyNote
 * For SQL `null` values, `null` is returned. For null-safety consider using
 * corresponding `*Opt` method.
 * @define returningNone
 * For SQL `null` values [[scala.None None]] is returned.
 * @define boolValues
 * - A single 'T', 'Y' or '1' character values or `1` numeric value are
 * considered `true`.
 * - A single 'F', 'N' or '0' character values or `0` numeric value are
 * considered `false`.
 */
public interface Row {

    /**
     * Returns an object of type `A` from column with a given index.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group generic
     * @usecase def col[A](idx: int): A
     * @inheritdoc
     */
    <T> T getCol(int idx, Class<T> cls);

    /**
     * Returns an object of type `A` from column with a given index.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group generic
     * @usecase def colOpt[A](idx: int): Optional<A>
     * @inheritdoc
     */
    <T> Optional<T> getColOpt(int idx, Class<T> cls);

    /**
     * Returns an object of type `A` from column with a given name.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group generic
     * @usecase def col[A](name: String): A
     * @inheritdoc
     */
    <T> T getCol(String name, Class<T> cls);

    /**
     * Returns an object of type `A` from column with a given name.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group generic
     * @usecase def colOpt[A](name: String): Optional<A>
     * @inheritdoc
     */
    <T> Optional<T> getColOpt(String name, Class<T> cls);

    /**
     * Returns a `String` from column with a given name.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group string
     */
    String getStr(String name);

    /**
     * Returns a `String` from column with a given name.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group string
     */
    Optional<String> getStrOpt(String name);

    /**
     * Returns a `String` from column with a given index.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group string
     */
    String getStr(int idx);

    /**
     * Returns a `String` from column with a given index.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group string
     */
    Optional<String> getStrOpt(int idx);

    /**
     * Returns a boolean value from column with a given name.
     * <p>
     * $boolValues
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group bool
     */
    Boolean getBool(String name);

    /**
     * Returns a boolean value from column with a given name.
     * <p>
     * $boolValues
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group bool
     */
    Optional<Boolean> getBoolOpt(String name);

    /**
     * Returns a boolean value from column with a given index.
     * <p>
     * $boolValues
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group bool
     */
    Boolean getBool(int idx);

    /**
     * Returns a boolean value from column with a given index.
     * <p>
     * $boolValues
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group bool
     */
    Optional<Boolean> getBoolOpt(int idx);

    /**
     * Returns a character from column with a given name.
     * <p>
     * Varchar types with a single character are convertible to a `Char`.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group char
     */
    Character getChar(String name);

    /**
     * Returns a character from column with a given name.
     * <p>
     * Varchar types with a single character are convertible to a `Char`.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group char
     */
    Optional<Character> getCharOpt(String name);

    /**
     * Returns a character from column with a given index.
     * <p>
     * Varchar types with a single character are convertible to a `Char`.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group char
     */
    Character getChar(int idx);

    /**
     * Returns a character from column with a given index.
     * <p>
     * Varchar types with a single character are convertible to a `Char`.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group char
     */
    Optional<Character> getCharOpt(int idx);

    /**
     * Returns a `Short` from column with a given name.
     * <p>
     * All numeric types can be converted to `Short`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group int
     */
    Short getShort(String name);

    /**
     * Returns a `Short` from column with a given name.
     * <p>
     * All numeric types can be converted to `Short`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    Optional<Short> getShortOpt(String name);

    /**
     * Returns a `Short` from column with a given index.
     * <p>
     * All numeric types can be converted to `Short`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group int
     */
    Short getShort(int idx);

    /**
     * Returns a `Short` from column with a given index.
     * <p>
     * All numeric types can be converted to `Short`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    Optional<Short> getShortOpt(int idx);

    /**
     * Returns an `int` from column with a given name.
     * <p>
     * All numeric types can be converted to `int`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group int
     */
    Integer getInt(String name);

    /**
     * Returns an `int` from column with a given name.
     * <p>
     * All numeric types can be converted to `int`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    OptionalInt getIntOpt(String name);

    /**
     * Returns an `int` from column with a given index.
     * <p>
     * All numeric types can be converted to `int`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    Integer getInt(int idx);

    /**
     * Returns an `int` from column with a given index.
     * <p>
     * All numeric types can be converted to `int`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    OptionalInt getIntOpt(int idx);

    /**
     * Returns a `Long` from column with a given name.
     * <p>
     * All numeric types can be converted to `Long`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group int
     */
    Long getLong(String name);

    /**
     * Returns a `Long` from column with a given name.
     * <p>
     * All numeric types can be converted to `Long`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    OptionalLong getLongOpt(String name);

    /**
     * Returns a `Long` from column with a given index.
     * <p>
     * All numeric types can be converted to `Long`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group int
     */
    Long getLong(int idx);

    /**
     * Returns a `Long` from column with a given index.
     * <p>
     * All numeric types can be converted to `Long`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group int
     */
    OptionalLong getLongOpt(int idx);

    /**
     * Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given name.
     * <p>
     * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
     * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
     * to be NaN use `numeric` method instead.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group unb
     */
    BigDecimal getBigDecimal(String name);

    /**
     * Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given name.
     * <p>
     * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
     * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
     * to be NaN use `numeric` method instead.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group unb
     */
    Optional<BigDecimal> getBigDecimalOpt(String name);

    /**
     * Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given index.
     * <p>
     * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
     * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
     * to be NaN use `numeric` method instead.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group unb
     */
    BigDecimal getBigDecimal(int idx);

    /**
     * Returns a [[scala.math.BigDecimal BigDecimal]] from column with a given index.
     * <p>
     * All numeric types can be converted to [[scala.math.BigDecimal BigDecimal]], note however that
     * NaN value is not representable by a [[scala.math.BigDecimal BigDecimal]]. If you expect values
     * to be NaN use `numeric` method instead.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group unb
     */
    Optional<BigDecimal> getBigDecimalOpt(int idx);

    /**
     * Returns a [[SqlNumeric]] from column with a given name.
     * <p>
     * All numeric types can be converted to [[SqlNumeric]].
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group unb
     */
    SqlNumeric getNumeric(String name);

    /**
     * Returns a [[SqlNumeric]] from column with a given name.
     * <p>
     * All numeric types can be converted to [[SqlNumeric]].
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group unb
     */
    Optional<SqlNumeric> getNumericOpt(String name);

    /**
     * Returns a [[SqlNumeric]] from column with a given index.
     * <p>
     * All numeric types can be converted to [[SqlNumeric]].
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group unb
     */
    SqlNumeric getNumeric(int idx);

    /**
     * Returns a [[SqlNumeric]] from column with a given index.
     * <p>
     * All numeric types can be converted to [[SqlNumeric]].
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group unb
     */
    Optional<SqlNumeric> getNumericOpt(int idx);

    /**
     * Returns a `Double` from column with a given name.
     * <p>
     * All numeric types can be converted to `Double`, but some conversions
     * may involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group float
     */
    Double getDouble(String name);

    /**
     * Returns a `Double` from column with a given name.
     * <p>
     * All numeric types can be converted to `Double`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group float
     */
    OptionalDouble getDoubleOpt(String name);

    /**
     * Returns a `Double` from column with a given index.
     * <p>
     * All numeric types can be converted to `Double`, but some conversions
     * may involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group float
     */
    Double getDouble(int idx);

    /**
     * Returns a `Double` from column with a given index.
     * <p>
     * All numeric types can be converted to `Double`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group float
     */
    OptionalDouble getDoubleOpt(int idx);

    /**
     * Returns a `Float` from column with a given name.
     * <p>
     * All numeric types can be converted to `Float`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group float
     */
    Float getFloat(String name);

    /**
     * Returns a `Float` from column with a given name.
     * <p>
     * All numeric types can be converted to `Float`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group float
     */
    Optional<Float> getFloatOpt(String name);

    /**
     * Returns a `Float` from column with a given index.
     * <p>
     * All numeric types can be converted to `Float`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group float
     */
    Float getFloat(int idx);

    /**
     * Returns a `Float` from column with a given index.
     * <p>
     * All numeric types can be converted to `Float`, but some conversions may
     * involve rounding or truncation.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group float
     */
    Optional<Float> getFloatOpt(int idx);

    /**
     * Returns an `Instant` from column with a given name.
     * <p>
     * Note that regular timestamp values are not convertible to an `Instant`
     * because timestamp values do not hold a time zone information.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    Instant getInstant(String name);

    /**
     * Returns an `Instant` from column with a given name.
     * <p>
     * Note that regular timestamp values are not convertible to an `Instant`
     * because timestamp values do not hold a time zone information.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<Instant> getInstantOpt(String name);

    /**
     * Returns an `Instant` from column with a given index.
     * <p>
     * Note that regular timestamp values are not convertible to an `Instant`
     * because timestamp values do not hold a time zone information.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    Instant getInstant(int idx);

    /**
     * Returns an `Instant` from column with a given index.
     * <p>
     * Note that regular timestamp values are not convertible to an `Instant`
     * because timestamp values do not hold a time zone information.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<Instant> getInstantOpt(int idx);

    /**
     * Returns a `LocalDateTime` from column with a given name.
     * <p>
     * For SQL date type that does not hold a time, `LocalDateTime` at start
     * of day is returned.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    LocalDateTime getLocalDateTime(String name);

    /**
     * Returns a `LocalDateTime` from column with a given name.
     * <p>
     * For SQL date type that does not hold a time, `LocalDateTime` at start
     * of day is returned.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<LocalDateTime> getLocalDateTimeOpt(String name);

    /**
     * Returns a `LocalDateTime` from column with a given index.
     * <p>
     * For SQL date type that does not hold a time, `LocalDateTime` at start
     * of day is returned.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    LocalDateTime getLocalDateTime(int idx);

    /**
     * Returns a `LocalDateTime` from column with a given index.
     * <p>
     * For SQL date type that does not hold a time, `LocalDateTime` at start
     * of day is returned.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<LocalDateTime> getLocalDateTimeOpt(int idx);

    /**
     * Returns a `LocalDate` from column with a given name.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalDate` - a time part is truncated.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    LocalDate getLocalDate(String name);

    /**
     * Returns a `LocalDate` from column with a given name.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalDate` - a time part is truncated.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<LocalDate> getLocalDateOpt(String name);

    /**
     * Returns a `LocalDate` from column with a given index.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalDate` - a time part is truncated.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    LocalDate getLocalDate(int idx);

    /**
     * Returns a `LocalDate` from column with a given index.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalDate` - a time part is truncated.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<LocalDate> getLocalDateOpt(int idx);

    /**
     * Returns a `LocalDate` from column with a given name.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalTime` - a date part is truncated.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    LocalTime getLocalTime(String name);

    /**
     * Returns a `LocalDate` from column with a given name.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalTime` - a date part is truncated.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<LocalTime> getLocalTimeOpt(String name);

    /**
     * Returns a `LocalDate` from column with a given index.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalTime` - a date part is truncated.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group date
     */
    LocalTime getLocalTime(int idx);

    /**
     * Returns a `LocalDate` from column with a given index.
     * <p>
     * SQL types that represent a date with a time are convertible to
     * `LocalTime` - a date part is truncated.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group date
     */
    Optional<LocalTime> getLocalTimeOpt(int idx);

    /**
     * Returns a byte array from column with a given name.
     * <p>
     * Note that this method cannot be used to fetch raw value of any type from
     * the database, it should be used only to fetch binary data.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group binary
     */
    byte[] getBytes(String name);

    /**
     * Returns a byte array from column with a given name.
     * <p>
     * Note that this method cannot be used to fetch raw value of any type from
     * the database, it should be used
     * only to fetch binary data.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group binary
     */
    Optional<byte[]> getBytesOpt(String name);

    /**
     * Returns a byte array from column with a given index.
     * <p>
     * Note that this method cannot be used to fetch raw value of any type from
     * the database, it should be used only to fetch binary data.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group binary
     */
    byte[] getBytes(int idx);

    /**
     * Returns a byte array from column with a given index.
     * <p>
     * Note that this method cannot be used to fetch raw value of any type from
     * the database, it should be used
     * only to fetch binary data.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group binary
     */
    Optional<byte[]> getBytesOpt(int idx);

    /**
     * Returns an `UUID` from column with a given name.
     * <p>
     * A string type with a standard UUID representation as defined by
     * `UUID.fromString` is convertible to UUID.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group uuid
     */
    UUID getUuid(String name);

    /**
     * Returns an `UUID` from column with a given name.
     * <p>
     * A string type with a standard UUID representation as defined by
     * `UUID.fromString` is convertible to UUID.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group uuid
     */
    Optional<UUID> getUuidOpt(String name);

    /**
     * Returns an `UUID` from column with a given index.
     * <p>
     * A string type with a standard UUID representation as defined by
     * `UUID.fromString` is convertible to UUID.
     * <p>
     * $nullSafetyNote
     * <p>
     * $exceptions
     *
     * @group uuid
     */
    UUID getUuid(int idx);

    /**
     * Returns an `UUID` from column with a given index.
     * <p>
     * A string type with a standard UUID representation as defined by
     * `UUID.fromString` is convertible to UUID.
     * <p>
     * $returningNone
     * <p>
     * $exceptions
     *
     * @group uuid
     */
    Optional<UUID> getUuidOpt(int idx);
}
