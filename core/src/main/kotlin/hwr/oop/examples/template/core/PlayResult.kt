package hwr.oop.examples.template.core

sealed interface PlayResult {
    data class Complete(val context: GameContext): PlayResult
    data class PendingEffect(val effect: CardEffect): PlayResult
}