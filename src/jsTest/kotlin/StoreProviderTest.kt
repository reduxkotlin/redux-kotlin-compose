package org.reduxkotlin.compose

import androidx.compose.runtime.getValue
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import org.reduxkotlin.Reducer
import org.reduxkotlin.createStore
import org.reduxkotlin.reducerForActionType
import org.w3c.dom.HTMLButtonElement
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ComposeWebExperimentalTestsApi::class)
class StoreProviderTest {
  data class State(val name: String, val age: Int)
  sealed interface Action {
    data class Rename(val name: String) : Action
    object CelebrateBirthday : Action
  }

  private val reducer: Reducer<State> = reducerForActionType<State, Action> { state, action ->
    when (action) {
      is Action.Rename -> state.copy(name = action.name)
      is Action.CelebrateBirthday -> state.copy(age = state.age + 1)
    }
  }

  @Test
  fun selectTest() = runTest {
    val store = createStore(reducer, State("Biggus", 69))
    composition {
      StoreProvider(store) {
        val name by selectState { name }
        val age by selectState { age }
        assertEquals(name, store.state.name)
        assertEquals(age, store.state.age)
      }
    }
  }

  @Test
  fun dispatchTest() = runTest {
    val store = createStore(reducer, State("Biggus", 69))
    composition {
      StoreProvider(store) {
        val name by selectState { name }
        val age by selectState { age }
        assertEquals(name, store.state.name)
        assertEquals(age, store.state.age)
        val dispatch = rememberDispatcher()
        Button(attrs = {
          id("target")
          onClick {
            dispatch(Action.CelebrateBirthday)
          }
        }) { Text("$age") }
      }
    }
    val el = (root.firstChild as HTMLButtonElement)
    val startAge = store.state.age
    assertEquals(el.textContent, "$startAge")
    el.click()
    waitForRecompositionComplete()
    assertEquals(el.textContent, "${startAge + 1}")
  }
}
