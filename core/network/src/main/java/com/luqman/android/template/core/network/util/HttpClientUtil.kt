package com.luqman.android.template.core.network.util

import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

object HttpClientUtil {
    inline fun <reified T> getClassType(): TypeInfo = typeInfo<T>()
}