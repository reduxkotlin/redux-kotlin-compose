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
  }
}
