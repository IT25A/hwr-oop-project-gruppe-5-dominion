package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.CardDefinition
import hwr.oop.examples.template.core.CardType

class Laboratory: CardDefinition {
    override val types: List<CardType> = listOf(CardType.ACTION)
    override val name: String = "Laboratory"
    override val cost: Int = 5
    override val draw: Int = 2
    override val actions: Int = 1
    override val buys: Int = 0
    override val gold: Int = 0
    override val points: Int = 0
}