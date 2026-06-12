package hwr.oop.examples.template.core

interface CardDefinition {
    val types: List<CardType>
    val name: String
    val cost: Int
    val draw: Int
    val actions: Int
    val buys: Int
    val gold: Int
    val points: Int

    fun beginAction(context: GameContext): PlayResult {
        return PlayResult.Complete(context)
    }

    fun endAction(context: GameContext, effect: ActiveEffect): PlayResult.Complete {
        return PlayResult.Complete(context)
    }
}
