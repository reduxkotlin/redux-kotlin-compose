package org.reduxkotlin.compose

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.reduxkotlin.createStore
import kotlin.test.Test
import kotlin.test.assertEquals

class StoreProviderTest {
  @get:Rule
  val compose = createComposeRule()

  @Test
  fun selectTest() = runBlocking {
    val store = createStore(TestReducer, TestState("Biggus", 69))
    compose.setContent {
      StoreProvider(store) {
        val name by selectState { name }
        val age by selectState { age }
        assertEquals(name, store.state.name)
        assertEquals(age, store.state.age)
      }
    }
    compose.awaitIdle()
  }

  @Test
  fun dispatchTest(): Unit = runBlocking {
    val store = createStore(TestReducer, TestState("Biggus", 69))
    compose.setContent {
      StoreProvider(store) {
        val name by selectState { name }
        val age by selectState { age }
        assertEquals(name, store.state.name)
        assertEquals(age, store.state.age)
        val dispatch = rememberDispatcher()
        Button(
          onClick = {
            dispatch(TestAction.CelebrateBirthday)
          },
          modifier = Modifier.testTag("target"),
        ) { Text("$age") }
      }
    }
    compose.awaitIdle()
    val el = compose.onNodeWithTag("target")
    val startAge = store.state.age
    el.assertTextEquals("$startAge")
    el.performClick()
    compose.awaitIdle()
    el.assertTextEquals("${startAge + 1}")
  }
}
