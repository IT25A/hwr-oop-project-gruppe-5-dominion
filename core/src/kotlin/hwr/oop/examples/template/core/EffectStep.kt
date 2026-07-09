package hwr.oop.examples.dominion

data class EffectStep(
    val explanation: String,
    val execute: (GameContext, List<AnsweredChoice>) -> CardEffect
)
