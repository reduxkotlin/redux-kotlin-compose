package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import org.reduxkotlin.Store
import org.reduxkotlin.TypedStore

@Suppress("PrivatePropertyName")
private val LocalStore: ProvidableCompositionLocal<TypedStore<*, *>> =
    compositionLocalOf { error("undefined") }

/**
 * Retrieves a [Store] from the current composition scope
 * @param State the type of the state the store is expected to hold
 * @return Retrieved [Store]
 * @see StoreProvider
 * @see rememberTypedStore
 */
@Composable
@Suppress("UNCHECKED_CAST")
public fun <State> rememberStore(): Store<State> = LocalStore.current as Store<State>

/**
 * Retrieves a [TypedStore] from the current composition scope
 * @param State the type of the state the store is expected to hold
 * @param State type
 * @param Action type
 * @return Retrieved [TypedStore]
 * @see StoreProvider
 * @see rememberStore
 */
@Composable
@Suppress("UNCHECKED_CAST")
public fun <State, Action> rememberTypedStore(): TypedStore<State, Action> =
    LocalStore.current as TypedStore<State, Action>

/**
 * Provides a given [store] to the child composition tree
 * @param store to provide
 * @param content to provide the store to
 * @param State type
 * @param Action type
 * @param Store type
 * @see rememberStore
 * @see rememberDispatcher
 * @see selectState
 */
@Composable
public fun <State, Action, Store : TypedStore<State, Action>> StoreProvider(
    store: Store,
    content: @Composable Store.() -> Unit
) {
    CompositionLocalProvider(LocalStore provides store) {
        store.content()
    }
}
