package com.example.benchmark

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun log() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inflater = LayoutInflater.from(context)
        val root = FrameLayout(context)

        val state = benchmarkRule.getState()
        while (state.keepRunning()) {
            // inflater.inflate(R.layout.view_test, root, false)
        }
    }
}
