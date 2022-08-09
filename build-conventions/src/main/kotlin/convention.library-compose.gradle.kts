plugins {
  id("convention.common")
  id("convention.compose")
  id("convention.library-mpp")
}

kotlin {
  sourceSets {
    named("commonMain") {
      dependencies {
        api(compose.runtime)
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
        implementation(compose.material)
        implementation(compose.uiTestJUnit4)
      }
    }
    named("androidTest") {
      dependencies {
//        implementation("androidx.compose.material:material:_")
//        implementation("androidx.compose.ui:ui-test-junit4:_")
//        implementation("androidx.test.espresso:espresso-core:_")
//        implementation("androidx.test:runner:_")
//        implementation("androidx.test:rules:_")
      }
    }
  }
}
