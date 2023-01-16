[![Release](https://github.com/reduxkotlin/redux-kotlin-compose/actions/workflows/release.yml/badge.svg)](https://github.com/reduxkotlin/redux-kotlin-compose/actions/workflows/release.yml)![badge][badge-android]
![badge][badge-js]
![badge][badge-jvm]
[![Slack chat](https://img.shields.io/badge/kotlinlang-%23redux-green?logo=slack&style=flat-square)](https://kotlinlang.slack.com/archives/C8A8G5F9Q)
[![Dokka docs](https://img.shields.io/badge/docs-dokka-orange?style=flat-square&logo=kotlin)](http://reduxkotlin.github.io/redux-kotlin-compose)
[![Version maven-central](https://img.shields.io/maven-central/v/org.reduxkotlin/redux-kotlin-compose?logo=apache-maven&style=flat-square)](https://mvnrepository.com/artifact/org.reduxkotlin/redux-kotlin-compose/latest)

# Redux-Kotlin-Compose

[Compose Multiplatform] integration for [Redux Kotlin]

## Installation

Artifacts are hosted on maven central. For multiplatform, add the following to your shared module:

```kotlin
kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation("org.reduxkotlin:redux-kotlin-compose:_")
      }
    }
  }
}
```

For JVM only:

```kotlin
dependencies {
  implementation("org.reduxkotlin:redux-kotlin-compose-jvm:_")
}
```

## Usage

```kotlin
data class State(val name: String? = null)
sealed interface Action {
  data class Rename(val name: String) : Action
  object ClearName : Action
}

val reducer: Reducer<State> = reducerForActionType<State, Action> { state, action ->
  when (action) {
    is Action.Rename -> state.copy(name = action.name)
    is Action.ClearName -> state.copy(name = null)
  }
}

@Composable
fun App() {
  StoreProvider(createStore(reducer, State())) {
    Component()
  }
}

@Composable
fun Component() {
  val name by selectState<State, String> { name }
  val dispatch = rememberDispatcher()
  Text(name)
  Button(
    text = "Clear",
    onClick = {
      dispatch(ClearName)
    }
  )
}


```

[badge-android]: http://img.shields.io/badge/platform-android-brightgreen.svg?style=flat-square

[badge-js]: http://img.shields.io/badge/platform-js-yellow.svg?style=flat-square

[badge-jvm]: http://img.shields.io/badge/platform-jvm-orange.svg?style=flat-square

[Compose Multiplatform]: https://github.com/JetBrains/compose-jb

[Redux Kotlin]: https://github.com/reduxkotlin/redux-kotlin
