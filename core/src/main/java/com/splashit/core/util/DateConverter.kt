package com.splashit.core.util

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateConverter {
    fun formatDate(date: String?): String {
        if (!date.isNullOrEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            try {
                val time: Long = sdf.parse(date).time
                val now = System.currentTimeMillis()
                return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                    .toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return ""
    }
}