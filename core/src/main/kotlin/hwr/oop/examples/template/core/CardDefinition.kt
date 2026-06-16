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

    fun getEffect(initial: GameContext): CardEffect? {
        return null
    }
}
