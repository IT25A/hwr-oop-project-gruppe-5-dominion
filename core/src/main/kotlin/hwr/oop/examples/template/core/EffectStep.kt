package hwr.oop.examples.template.core

data class EffectStep(
    val explanation: String,
    val execute: (GameContext, List<AnsweredChoice>) -> CardEffect
)
