package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.model.enums.GeocacheSize
import locus.api.objects.geocaching.GeocachingData
import javax.inject.Inject

class LocusMapContainerConverter @Inject constructor() {
    fun convert(size: GeocacheSize?): Int = when(size) {
        GeocacheSize.NANO, GeocacheSize.MICRO -> GeocachingData.CACHE_SIZE_MICRO
        GeocacheSize.SMALL -> GeocachingData.CACHE_SIZE_SMALL
        GeocacheSize.REGULAR -> GeocachingData.CACHE_SIZE_REGULAR
        GeocacheSize.LARGE -> GeocachingData.CACHE_SIZE_LARGE
        GeocacheSize.XLARGE -> GeocachingData.CACHE_SIZE_HUGE
        GeocacheSize.OTHER -> GeocachingData.CACHE_SIZE_OTHER
        GeocacheSize.NONE -> GeocachingData.CACHE_SIZE_NOT_CHOSEN
        else -> GeocachingData.CACHE_SIZE_OTHER
    }
}