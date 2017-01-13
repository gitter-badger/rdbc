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

import io.rdbc.ImmutSeq
/*
/**
  * A registry holding a map of type converters.
  *
  * @param converters type converters. Map keys are conversion target classes.
  */
class TypeConverterRegistryWrapper(converters: Map[Class[_], TypeConverterWrapper[_]]) {

  /** Gets a converter by a given class. */
  def getByClass[T](cls: Class[T]): Option[TypeConverterWrapper[T]] = {
    converters.get(cls).asInstanceOf[Option[TypeConverterWrapper[T]]]
  }
}

/** Factory of TypeConverterRegistry */
object TypeConverterRegistryWrapper {

  /** Returns a type converter registry from converters given. */
  def apply(converters: TypeConverterWrapper[_]*): TypeConverterRegistryWrapper = {
    val registry: Map[Class[_], TypeConverterWrapper[_]] = Map(
      converters.map(conv => conv.cls -> conv): _*
    )
    new TypeConverterRegistryWrapper(registry)
  }

  /** Returns a type converter registry from converters given. */
  def apply(converters: ImmutSeq[TypeConverterWrapper[_]]): TypeConverterRegistryWrapper = {
    apply(converters: _*)
  }
}
*/