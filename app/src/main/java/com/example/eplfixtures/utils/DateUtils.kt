package com.example.eplfixtures.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    private const val API_PATTERN = "yyyy-MM-dd HH:mm:ss'Z'"
    private const val DISPLAY_DATE_PATTERN = "dd MMM yyyy"
    private const val DISPLAY_TIME_PATTERN = "HH:mm"

    private fun parseUtc(dateUtc: String): Date? {
        val format = SimpleDateFormat(API_PATTERN, Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return try {
            format.parse(dateUtc)
        } catch (e: ParseException) {
            null
        }
    }

    fun toLocalDateTime(dateUtc: String): String {
        val date = parseUtc(dateUtc) ?: return dateUtc
        val dateFormat = SimpleDateFormat(DISPLAY_DATE_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        val timeFormat = SimpleDateFormat(DISPLAY_TIME_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        return "${dateFormat.format(date)}, ${timeFormat.format(date)}"
    }

    fun toLocalDate(dateUtc: String): String {
        val date = parseUtc(dateUtc) ?: return dateUtc
        val dateFormat = SimpleDateFormat(DISPLAY_DATE_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        return dateFormat.format(date)
    }

    fun toLocalTime(dateUtc: String): String {
        val date = parseUtc(dateUtc) ?: return dateUtc
        val timeFormat = SimpleDateFormat(DISPLAY_TIME_PATTERN, Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
        return timeFormat.format(date)
    }
}
