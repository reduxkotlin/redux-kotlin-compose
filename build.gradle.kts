plugins {
  id("convention.library-compose")
  id("convention.publishing-nexus")
  id("convention.publishing-mpp")
  if (System.getenv("CI") == null) id("convention.git-hooks")
}

gradleEnterprise {
  buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
  }
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
        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
      }
    }
  }
}
