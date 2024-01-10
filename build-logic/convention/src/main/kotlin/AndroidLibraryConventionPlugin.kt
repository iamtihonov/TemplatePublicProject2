/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import ua.artem.template.configureKotlinAndroid
import ua.artem.template.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("io.gitlab.arturbosch.detekt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                defaultConfig {
                    testInstrumentationRunner =
                        "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            dependencies {
                add("testImplementation", libs.findLibrary("junit.java").get())
                add("androidTestImplementation", libs.findLibrary("junit.android").get())

                add("testImplementation", libs.findLibrary("mockito").get())
                add("androidTestUtil", libs.findLibrary("orchestrator").get())
                add("androidTestImplementation", libs.findLibrary("uiautomator").get())

                add("androidTestImplementation", libs.findLibrary("espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("espresso.intents").get())
                add("implementation", libs.findLibrary("espresso.idling").get())
                add("androidTestImplementation", libs.findLibrary("espresso.contrib").get())
                add("testImplementation", libs.findLibrary("robolectric").get())
            }
        }
    }
}
