package com.example.calendarsymbersoft.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EventTest {

    @Test
    fun `negativeDayId returns false`() {
        val event = Event(
            id = 1,
            dayId = -1,
            title = "Test",
            timeFrom = 1,
            timeTo = 2,
            description = ""
        )
        assertThat(event.validFields()).isFalse()
    }

    @Test
    fun `empty title returns false`() {
        val event = Event(
            id = 1,
            dayId = 1,
            title = "",
            timeFrom = 1,
            timeTo = 2,
            description = ""
        )
        assertThat(event.validFields()).isFalse()
    }

    @Test
    fun `timeFrom is more than timeTo returns false`() {
        val event = Event(
            id = 1,
            dayId = 1,
            title = "TestTitle",
            timeFrom = 3,
            timeTo = 2,
            description = "TestDescription"
        )
        assertThat(event.validFields()).isFalse()
    }

    @Test
    fun `valid params returns true`() {
        val event = Event(
            id = 1,
            dayId = 1,
            title = "TestTitle",
            timeFrom = 1,
            timeTo = 2,
            description = ""
        )
        assertThat(event.validFields()).isTrue()
    }

    @Test
    fun `timeFrom is less than zero returns false`() {
        val event = Event(
            id = 1,
            dayId = 1,
            title = "TestTitle",
            timeFrom = -1,
            timeTo = 2,
            description = "TestDescription"
        )
        assertThat(event.validFields()).isFalse()
    }
}