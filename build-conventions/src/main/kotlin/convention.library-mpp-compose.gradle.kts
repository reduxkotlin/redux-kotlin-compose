import util.jvmCommonTest

plugins {
    id("convention.common")
    id("convention.mpp-compose")
    id("convention.library-android")
    id("convention.control")
    id("org.jetbrains.compose")
}

kotlin {
    explicitApi()
    android {
        if (!CI || SANDBOX || isMainHost) {
            publishLibraryVariants("release", "debug")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
            }
        }
        named("commonTest") {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        named("jsTest") {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.web.testUtils)
            }
        }
        named("jvmTest") {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        named("androidMain") {
            val jvmCommonMain by getting
            kotlin.srcDir(jvmCommonMain.kotlin)
            resources.srcDir(jvmCommonMain.resources)
        }
        named("androidUnitTest") {
            val jvmCommonTest by getting
            kotlin.srcDir(jvmCommonTest.kotlin)
            resources.srcDir(jvmCommonTest.resources)
        }
        named("jsTest") {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        jvmCommonTest {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(compose.material)
                implementation(compose.uiTestJUnit4)
            }
        }
        named("androidInstrumentedTest") {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(compose.material)
                implementation(compose.uiTestJUnit4)
            }
        }
    }
}
