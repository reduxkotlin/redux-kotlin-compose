package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import org.reduxkotlin.createStore
import org.reduxkotlin.createTypedStore
import org.reduxkotlin.typedReducer
import test.TestState
import test.testReducer
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class TypedProviderTest {
    abstract fun runComposeTest(block: @Composable () -> Unit)

    @Test
    fun upcastStore() = runComposeTest {
        val store = createStore(typedReducer(testReducer), TestState())
        StoreProvider(store) {
            val rDispatcher = rememberTypedDispatcher<Any>()
            val rStore = rememberTypedStore<TestState, Any>()
            assertEquals(rDispatcher, rStore.dispatch)
        }
    }

    @Test
    fun downcastStore() = runComposeTest {
        val store = createTypedStore(testReducer, TestState())
        StoreProvider(store) {
            val rDispatcher = rememberDispatcher()
            val rStore = rememberStore<TestState>()
            assertEquals(rDispatcher, rStore.dispatch)
        }
    }
}
