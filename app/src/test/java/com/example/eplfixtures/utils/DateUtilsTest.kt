package com.example.eplfixtures.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.TimeZone

class DateUtilsTest {

    @Test
    fun `toLocalDate returns readable date for valid UTC string`() {
        val original = TimeZone.getDefault()
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
            val result = DateUtils.toLocalDate("2023-08-11 19:00:00Z")
            assertEquals("11 Aug 2023", result)
        } finally {
            TimeZone.setDefault(original)
        }
    }

    @Test
    fun `toLocalTime returns readable time for valid UTC string`() {
        val original = TimeZone.getDefault()
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
            val result = DateUtils.toLocalTime("2023-08-11 19:00:00Z")
            assertEquals("19:00", result)
        } finally {
            TimeZone.setDefault(original)
        }
    }

    @Test
    fun `invalid date string is returned unchanged instead of crashing`() {
        val malformed = "not-a-date"
        val result = DateUtils.toLocalDate(malformed)
        assertTrue(result == malformed)
    }
}
