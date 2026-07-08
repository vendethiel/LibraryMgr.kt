package org.vendethiel.librarymgr.library.exception

import kotlin.reflect.KClass

data class ResultSerializationError(val clazz: KClass<*>, val field: String) : RuntimeException("Cannot serialize ${clazz.simpleName} to result because $field is incorrect")
