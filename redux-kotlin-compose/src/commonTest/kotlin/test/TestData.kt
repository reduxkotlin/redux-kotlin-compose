package test

import org.reduxkotlin.TypedReducer

data class TestState(val name: String = "Biggus", val age: Int = 69)

sealed interface TestAction {
    data class Rename(val name: String) : TestAction
    object CelebrateBirthday : TestAction
}

val testReducer: TypedReducer<TestState, TestAction> = { state, action ->
    when (action) {
        is TestAction.Rename -> state.copy(name = action.name)
        is TestAction.CelebrateBirthday -> state.copy(age = state.age + 1)
    }
}
