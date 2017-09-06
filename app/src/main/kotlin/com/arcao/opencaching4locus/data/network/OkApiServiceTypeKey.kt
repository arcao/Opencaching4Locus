package com.arcao.opencaching4locus.data.network

import dagger.MapKey

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class OkApiServiceTypeKey(val value : OkApiServiceType)