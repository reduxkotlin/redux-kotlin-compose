import util.targetGroup

plugins {
    id("convention.common")
    kotlin("multiplatform")
}

kotlin {
    js {
        useCommonJs()
        browser { testTask { useKarma() } }
    }
    targetGroup(
        name = "jvmCommon",
        mainSourceSetTarget = "commonMain",
        testSourceSetTarget = "commonTest",
        jvm(),
    )
}
