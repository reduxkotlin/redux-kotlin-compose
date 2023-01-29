package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.reduxkotlin.TypedStore

/**
 * Selects a value from the local store.
 * @param selector to extract the value
 * @param State state type
 * @param Slice extracted value type
 * @return selected value
 */
@Composable
public inline fun <reified State, Slice> selectState(
    crossinline selector: @DisallowComposableCalls State.() -> Slice
): androidx.compose.runtime.State<Slice> {
    return rememberStore<State>().selectState(selector)
}

/**
 * Selects a value from the local store.
 * @receiver a store to extract the value from
 * @param selector to extract the value
 * @param State state type
 * @param Slice extracted value type
 * @return selected value
 */
@Composable
public inline fun <State, Slice> TypedStore<State, *>.selectState(
    crossinline selector: @DisallowComposableCalls State.() -> Slice
): androidx.compose.runtime.State<Slice> {
    val result = remember { mutableStateOf(state.selector()) }
    DisposableEffect(result) {
        val unsubscribe = subscribe { result.value = state.selector() }
        onDispose(unsubscribe)
    }
    return result
}
