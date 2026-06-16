package hwr.oop.examples.template.core

sealed interface GamePhase {
    object ActionPhase : GamePhase
    object BuyPhase : GamePhase
    object EffectActive : GamePhase
}