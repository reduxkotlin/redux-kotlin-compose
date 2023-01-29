package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.TypedDispatcher

/**
 * Retrieves a [Dispatcher] from the current local store
 * @return retrieved [Dispatcher]
 * @see StoreProvider
 * @see rememberTypedDispatcher
 */
@Composable
public fun rememberDispatcher(): Dispatcher = rememberStore<Any>().dispatch

/**
 * Retrieves a [Dispatcher] from the current local store
 * @return retrieved [Dispatcher]
 * @see StoreProvider
 * @see rememberDispatcher
 */
@Composable
public fun <Action> rememberTypedDispatcher(): TypedDispatcher<Action> =
    rememberTypedStore<Any, Action>().dispatch
