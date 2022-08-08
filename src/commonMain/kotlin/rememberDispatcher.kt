package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import org.reduxkotlin.Dispatcher

/**
 * Retrieves a [Dispatcher] from the current local store
 * @return retrieved [Dispatcher]
 */
@Composable
public fun rememberDispatcher(): Dispatcher = rememberStore<Any>().dispatch
