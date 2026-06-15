package hwr.oop.examples.template.core

sealed interface GameStatus {
    object ActionPhase : GameStatus
    object BuyPhase : GameStatus
    data class EffectActive(
        val effect: ActiveEffect
    ) : GameStatus
}