package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.CardDefinition
import hwr.oop.examples.dominion.CardType

class Copper: CardDefinition {
    override val types: List<CardType> = listOf(CardType.TREASURE)
    override val name: String = "Copper"
    override val cost: Int = 0
    override val draw: Int = 0
    override val actions: Int = 0
    override val buys: Int = 0
    override val gold: Int = 1
    override val points: Int = 0
}