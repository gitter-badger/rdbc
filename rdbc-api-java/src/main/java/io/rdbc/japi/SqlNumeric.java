package io.rdbc.japi;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public final class SqlNumeric {

    private enum Type {
        NAN, POS_INF, NEG_INF, FINITE
    }

    private final Type type;
    private final BigDecimal value;

    public static final SqlNumeric NAN = new SqlNumeric(Type.NAN, null);
    public static final SqlNumeric NEG_INFINITY = new SqlNumeric(Type.NEG_INF, null);
    public static final SqlNumeric POS_INFINITY = new SqlNumeric(Type.POS_INF, null);

    public static SqlNumeric of(BigDecimal value) {
        return new SqlNumeric(Type.FINITE, value);
    }

    private SqlNumeric(Type type, BigDecimal value) {
        this.type = type;
        this.value = value;
    }

    public boolean isNaN() {
        return type == Type.NAN;
    }

    public boolean isPosInifinity() {
        return type == Type.POS_INF;
    }

    public boolean isNegInifinity() {
        return type == Type.NEG_INF;
    }

    public boolean isFinite() {
        return type == Type.FINITE;
    }

    public BigDecimal getValue() {
        if (value == null) {
            throw new NoSuchElementException("SqlNumeric value is " + type.name());
        } else {
            return value;
        }
    }
}

/**
 * General unbounded numeric type that extends [[scala.math.BigDecimal BigDecimal]] to be able to
 * represent NaN, positive infitnity and negative infinity.
 */
/*

class SqlNumeric {

    def isNaN: Boolean


  case object NaN extends SqlNumeric


  case object PosInfinity extends SqlNumeric


  case object NegInfinity extends SqlNumeric


  case class Val(bigDecimal: BigDecimal) extends SqlNumeric
}

 */