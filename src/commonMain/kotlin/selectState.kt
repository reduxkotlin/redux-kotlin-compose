package org.reduxkotlin.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.reduxkotlin.Store

/**
 * Selects a value from the local store.
 * @param selector to extract the value
 * @param TState state type
 * @param TSlice extracted value type
 * @return selected value
 */
@Composable
public inline fun <reified TState, TSlice> selectState(
  crossinline selector: @DisallowComposableCalls TState.() -> TSlice
): State<TSlice> {
  return rememberStore<TState>().selectState(selector)
}

/**
 * Selects a value from the local store.
 * @receiver a store to extract the value from
 * @param selector to extract the value
 * @param TState state type
 * @param TSlice extracted value type
 * @return selected value
 */
@Composable
public inline fun <TState, TSlice> Store<TState>.selectState(
  crossinline selector: @DisallowComposableCalls TState.() -> TSlice
): State<TSlice> {
  val result = remember { mutableStateOf(state.selector()) }
  DisposableEffect(result) {
    val unsubscribe = subscribe { result.value = state.selector() }
    onDispose(unsubscribe)
  }
  return result
}
