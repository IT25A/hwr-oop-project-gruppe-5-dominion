package hwr.oop.examples.dominion

interface CardDefinition {
    val types: List<CardType>
    val name: String
    val cost: Int
    val draw: Int
    val actions: Int
    val buys: Int
    val gold: Int
    val points: Int

    fun customPointFunction(ctx: GameContext): Int = 0
    fun getEffect(initial: GameContext): CardEffect? {
        return null
    }
}
