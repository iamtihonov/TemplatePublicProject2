package ua.good.network

import android.util.Base64
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreatePasswordHashInstrumentedTest {

    @Test
    fun createPasswordHash() {
        val passwordHash = String(Base64.encode("1".toByteArray(), Base64.DEFAULT))
        println("passwordHashLength = ${passwordHash.length}")
        println("char = $passwordHash")
    }
}
