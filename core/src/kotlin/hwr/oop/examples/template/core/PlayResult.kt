package hwr.oop.examples.dominion

sealed interface PlayResult {
    data class Complete(val context: GameContext): PlayResult
    data class PendingEffect(val effect: CardEffect): PlayResult
}