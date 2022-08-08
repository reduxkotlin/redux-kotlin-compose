plugins {
  id("convention.common")
  kotlin("multiplatform")
}

kotlin {
  jvm()
  js(IR) {
    useCommonJs()
    browser {
      testTask {
        useKarma {
          useFirefoxHeadless()
        }
      }
    }
  }
}
