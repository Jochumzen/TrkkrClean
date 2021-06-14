package com.trkkr.trkkrclean.utilities

import ch.poole.openinghoursparser.I18n
import ch.poole.openinghoursparser.OpeningHoursParseException
import ch.poole.openinghoursparser.OpeningHoursParser
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.util.*

class OpeningTest {

    @Before
    fun setUp() {
        I18n.setLocale(Locale.ROOT)
    }

    @Test
    fun ohTest() = try {
        val parser =
            OpeningHoursParser(ByteArrayInputStream("Mo-Su 10:00-18:00; PH 10:00-12:00, PH Mo Off".toByteArray()))
        val rules = parser.rules(false)

        //Does not handle day events such as sunrise, sunset -> returns null
        val y = rules.evaluate(Date())
        Assert.assertEquals(null, y)

    } catch (pex: OpeningHoursParseException) {
        Assert.fail(pex.message)
    }
}