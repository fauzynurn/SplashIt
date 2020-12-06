package com.splashit.core

import com.splashit.core.util.DateConverter
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun date_isCorrect() {
        assertEquals("abc", DateConverter.formatDate("2019-03-25T12:30:46-04:00"))
    }
}