plugins {
    id("convention.library-mpp-compose")
    id("convention.publishing-mpp")
}

android {
    namespace = "org.reduxkotlin.compose"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api("org.reduxkotlin:redux-kotlin:_")
            }
        }
        named("commonTest") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
            }
        }
        named("androidInstrumentedTest") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
            }
        }
    }
}
