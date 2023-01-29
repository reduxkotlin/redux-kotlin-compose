package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import org.reduxkotlin.createStore
import org.reduxkotlin.createTypedStore
import org.reduxkotlin.typedReducer
import org.w3c.dom.HTMLButtonElement
import test.*
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ComposeWebExperimentalTestsApi::class)
class StoreProviderTest : TypedProviderTest() {
    @Test
    fun selectTest() = runTest {
        val store = createStore(typedReducer(testReducer), TestState("Biggus", 69))
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
        val store = createTypedStore(testReducer, TestState("Biggus", 69))
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
                        dispatch(TestAction.CelebrateBirthday)
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

    override fun runComposeTest(block: @Composable () -> Unit) = runTest {
        composition(block)
    }
}
