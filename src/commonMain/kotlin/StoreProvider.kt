package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import org.reduxkotlin.Store

private val LocalStore: ProvidableCompositionLocal<Store<*>> = compositionLocalOf { error("undefined") }

/**
 * Retrieves a [Store] from the current composition scope
 * @param TState the type of the state the store is expected to hold
 * @return Retrieved [Store]
 * @see StoreProvider
 */
@Composable
@Suppress("UNCHECKED_CAST")
public fun <TState> rememberStore(): Store<TState> = LocalStore.current as Store<TState>

/**
 * Provides a given [store] to the child composition tree
 * @param store to provide
 * @param content to provide the store to
 * @see rememberStore
 * @see rememberDispatcher
 * @see dispatchAction
 * @see selectState
 */
@Composable
@Suppress("FunctionName")
public fun <T : Any> StoreProvider(store: Store<T>, content: @Composable Store<T>.() -> Unit) {
  CompositionLocalProvider(LocalStore provides store) {
    store.content()
  }
}
