package com.arcao.opencaching4locus.data.retrofit.converter

import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.lang.reflect.Type

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ArraySeparator

class ArraySeparatorConverterFactory : Factory() {
    companion object {
        fun create() : Factory = ArraySeparatorConverterFactory()
    }

    override fun stringConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, String>? {
        if (annotations == null)
            return null

        if (annotations.any { it is ArraySeparator }) {
            return ArraySeparatorConverter.INSTANCE
        }

        return null
    }

    private class ArraySeparatorConverter : Converter<Any,  String> {
        companion object {
            val INSTANCE = ArraySeparatorConverter()
        }

        override fun convert(value: Any): String = when(value) {
            is Array<*> -> value.joinToString("|")
            is Iterable<*> ->value.joinToString("|")
            else -> value.toString()
        }
    }
}