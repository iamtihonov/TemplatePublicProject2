package ua.good.utils

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UtilsModuleUnitTest {

    @Test
    fun testEmailCheck() {
        Assert.assertTrue("123@gmail.com".isEmailValid())
        Assert.assertTrue("asdgjdufuj@mail.net".isEmailValid())
        Assert.assertFalse("@gmail.com".isEmailValid())
        Assert.assertFalse("gmail.com".isEmailValid())
    }
}
