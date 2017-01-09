package io.rdbc.javawrappers

import java.util.concurrent.TimeUnit

import io.rdbc.japi.Row
import io.rdbc.sapi.SqlNumeric.{NaN, NegInfinity, PosInfinity, Val}
import io.rdbc.{japi, sapi}

import scala.collection.JavaConverters._
import scala.collection.immutable.Seq
import scala.compat.java8.OptionConverters._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

private[javawrappers] object Conversions {

  implicit class SqlNumericToJava(value: sapi.SqlNumeric) {
    def asJava: japi.SqlNumeric = {
      value match {
        case NaN => japi.SqlNumeric.NAN
        case NegInfinity => japi.SqlNumeric.NEG_INFINITY
        case PosInfinity => japi.SqlNumeric.POS_INFINITY
        case Val(bigDec) => japi.SqlNumeric.of(bigDec.bigDecimal)
      }
    }
  }

  implicit class JavaDurationToScala(value: java.time.Duration) {
    def asScala: FiniteDuration = {
      FiniteDuration(value.toNanos, TimeUnit.NANOSECONDS)
    }
  }

  implicit class WarningToJava(value: sapi.Warning) {
    def asJava: japi.Warning = {
      japi.Warning.of(value.msg, value.code)
    }
  }

  implicit class ColumnMetadataToJava(value: sapi.ColumnMetadata) {
    def asJava: japi.ColumnMetadata = {
      new japi.ColumnMetadata(value.name, value.dbTypeId, value.cls.asJava)
    }
  }

  implicit class RowMetadataToJava(value: sapi.RowMetadata) {
    def asJava: japi.RowMetadata = {
      japi.RowMetadata.of(value.columns.map(_.asJava).asJava)
    }
  }

  implicit class ResultSetToJava(value: sapi.ResultSet) {
    def asJava: japi.ResultSet = {
      japi.ResultSet.of(
        value.rowsAffected,
        value.warnings.map(_.asJava).asJava,
        value.metadata.asJava,
        value.rows.map[Row, Seq[Row]](new RowWrapper(_)).asJava
      )
    }
  }

  implicit class ResultStreamToJava(value: sapi.ResultStream) {
    def asJava(implicit ec: ExecutionContext): japi.ResultStream = {
      new ResultStreamWrapper(value)
    }
  }

  implicit class RowToJava(value: sapi.Row) {
    def asJava: japi.Row = {
      new RowWrapper(value)
    }
  }

  implicit class SelectToJava(value: sapi.Select) {
    def asJava(implicit ec: ExecutionContext): japi.Select = {
      new SelectWrapper(value)
    }
  }

  implicit class InsertToJava(value: sapi.Insert) {
    def asJava(implicit ec: ExecutionContext): japi.Insert = {
      new InsertWrapper(value)
    }
  }

  implicit class DeleteToJava(value: sapi.Delete) {
    def asJava(implicit ec: ExecutionContext): japi.Delete = {
      new DeleteWrapper(value)
    }
  }

  implicit class ConnectionToJava(value: sapi.Connection) {
    def asJava(implicit ec: ExecutionContext): japi.Connection = {
      new ConnectionWrapper(value)
    }
  }

}
