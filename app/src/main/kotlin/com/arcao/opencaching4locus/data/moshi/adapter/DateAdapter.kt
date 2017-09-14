package com.arcao.opencaching4locus.data.moshi.adapter

import com.arcao.opencaching4locus.ui.base.util.fromIso8601
import com.arcao.opencaching4locus.ui.base.util.toIso8901
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class DateAdapter {
    @ToJson
    fun toJson(date : Date) : String {
        return date.toIso8901()
    }

    @FromJson
    fun fromJson(date: String) : Date {
        return date.fromIso8601()
    }

}