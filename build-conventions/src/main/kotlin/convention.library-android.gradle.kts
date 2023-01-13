plugins {
  id("convention.common")
  id("com.android.library")
}

android {
  compileSdk = 32
  defaultConfig {
    minSdk = 21
    targetSdk = 32
//    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    publishing {
      multipleVariants {
        withSourcesJar()
        withJavadocJar()
        allVariants()
      }
    }
  }
}
