package com.arcao.opencaching4locus.data.network.converter

import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import java.lang.reflect.Type

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class ArraySeparator

class ArraySeparatorConverterFactory : Factory() {
    companion object {
        fun create() : Factory {
            return ArraySeparatorConverterFactory()
        }
    }

    override fun stringConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, String>? {
        if (annotations == null)
            return null

        if (annotations.any { it is ArraySeparator }) {
            return ArraySeparatorConverter.INSTANCE
        }

        return null
    }

    private class ArraySeparatorConverter : Converter<Array<Any>,  String> {
        companion object {
            val INSTANCE = ArraySeparatorConverter()
        }

        override fun convert(value: Array<Any>): String {
            return value.joinToString("|")
        }
    }
}