import util.withName

plugins {
  id("convention.mpp")
  id("convention.library-android")
  id("convention.control")
}

kotlin {
  explicitApi()
  android {
    if (!CI || SANDBOX || isMainHost) {
      publishLibraryVariants("release", "debug")
    }
  }

  sourceSets {
    named("commonTest") {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    named("jsTest") {
      dependencies {
        implementation(kotlin("test-js"))
      }
    }
    named("jvmTest") {
      dependencies {
        implementation(kotlin("test-junit5"))
      }
    }
    withName("androidTest") {
      dependencies {
        implementation(kotlin("test-junit5"))
      }
    }
    all {
      languageSettings {
        optIn("kotlin.RequiresOptIn")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
      }
    }
  }
}
