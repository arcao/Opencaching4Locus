package com.arcao.opencaching4locus.data.okapi

import dagger.MapKey

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ForOkApiService(val value : OkApiServiceType)