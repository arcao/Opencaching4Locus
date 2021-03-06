package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.PerApp
import com.arcao.opencaching4locus.model.response.Image
import locus.api.objects.geocaching.GeocachingImage
import javax.inject.Inject

@PerApp
class LocusMapImageConverter @Inject constructor() {
    fun convert(image: Image): GeocachingImage? = GeocachingImage().apply {
        name = image.caption
        url = image.url
        thumbUrl = image.thumbnailUrl
    }

}