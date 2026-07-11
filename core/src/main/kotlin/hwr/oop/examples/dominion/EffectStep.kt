package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
data class EffectStep(
    val explanation: String,
    val execute: (GameContext, List<AnsweredChoice>) -> CardEffect
)
