package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.CardDefinition
import hwr.oop.examples.template.core.CardType

class Silver: CardDefinition {
    override val types: List<CardType> = listOf(CardType.TREASURE)
    override val name: String = "Silver"
    override val cost: Int = 3
    override val draw: Int = 0
    override val actions: Int = 0
    override val buys: Int = 0
    override val gold: Int = 2
    override val points: Int = 0
}