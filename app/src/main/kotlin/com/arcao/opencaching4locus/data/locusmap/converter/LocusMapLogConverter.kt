package com.arcao.opencaching4locus.data.locusmap.converter

import com.arcao.opencaching4locus.model.enums.LogType
import com.arcao.opencaching4locus.model.response.Log
import locus.api.objects.geocaching.GeocachingLog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocusMapLogConverter @Inject constructor(
        private val imageConverter: LocusMapImageConverter
) {
    fun convert(log: Log): GeocachingLog {
        return GeocachingLog().apply {
            //id = log.uuid
            type = convertType(log.type)
            date = log.date?.time ?: 0
            finder = log.user?.username
            findersFound = log.user?.cachesFound ?: 0
            logText = log.comment

            log.images.forEach {
                addImage(imageConverter.convert(it))
            }
        }
    }

    private fun convertType(type: LogType): Int = when(type) {
        LogType.ARCHIVED -> GeocachingLog.CACHE_LOG_TYPE_ARCHIVE
        LogType.ATTENDED -> GeocachingLog.CACHE_LOG_TYPE_ATTENDED
        LogType.COMMENT -> GeocachingLog.CACHE_LOG_TYPE_WRITE_NOTE
        LogType.DIDNT_FIND_IT -> GeocachingLog.CACHE_LOG_TYPE_NOT_FOUND
        LogType.FOUND_IT -> GeocachingLog.CACHE_LOG_TYPE_FOUND
        LogType.LOCKED -> GeocachingLog.CACHE_LOG_TYPE_WRITE_NOTE
        LogType.MAINTENANCE_PERFORMED -> GeocachingLog.CACHE_LOG_TYPE_OWNER_MAINTENANCE
        LogType.MOVED -> GeocachingLog.CACHE_LOG_TYPE_UPDATE_COORDINATES
        LogType.NEEDS_MAINTENANCE -> GeocachingLog.CACHE_LOG_TYPE_NEEDS_MAINTENANCE
        LogType.OC_TEAM_COMMENT -> GeocachingLog.CACHE_LOG_TYPE_POST_REVIEWER_NOTE
        LogType.READY_TO_SEARCH -> GeocachingLog.CACHE_LOG_TYPE_ANNOUNCEMENT
        LogType.TEMPORARILY_UNAVAILABLE -> GeocachingLog.CACHE_LOG_TYPE_TEMPORARILY_DISABLE_LISTING
        LogType.WILL_ATTEND -> GeocachingLog.CACHE_LOG_TYPE_WILL_ATTEND
    }

}