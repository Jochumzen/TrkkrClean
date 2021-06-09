package com.trkkr.trkkrclean.utilities

import org.junit.Test

import org.junit.Assert.*

class OpeningHoursTest {

    @Test
    fun createOpeningHours() {
        val oh = "Mo 10:00-12:00,12:30-15:00; Tu-Fr 08:00-12:00,12:30-15:00; Sa 08:00-12:00"
        val actual = OpeningHours(oh, listOf(
            OpeningHours.RuleSet("Mo 10:00-12:00,12:30-15:00"),
            OpeningHours.RuleSet("Tu-Fr 08:00-12:00,12:30-15:00"),
            OpeningHours.RuleSet("Sa 08:00-12:00")
        ))
        val expected = OpeningHours.createOpeningHours(oh)
        assertEquals(expected, actual)

    }
}