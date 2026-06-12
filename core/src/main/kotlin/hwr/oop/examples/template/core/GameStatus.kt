package hwr.oop.examples.template.core

sealed interface GameStatus {
    object Running : GameStatus
    data class WaitingForChoice(
        val effect: ActiveEffect
    ) : GameStatus
}