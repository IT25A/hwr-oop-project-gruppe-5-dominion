package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.CardDefinition
import hwr.oop.examples.dominion.CardType

class  Market: CardDefinition {
    override val types: List<CardType> = listOf(CardType.ACTION)
    override val name: String = "GameMarket"
    override val cost: Int = 5
    override val draw: Int = 1
    override val actions: Int = 1
    override val buys: Int = 1
    override val gold: Int = 1
    override val points: Int = 0
}