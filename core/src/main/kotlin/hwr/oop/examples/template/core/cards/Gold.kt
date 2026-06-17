package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.CardDefinition
import hwr.oop.examples.template.core.CardType

class Gold: CardDefinition {
    override val types: List<CardType> = listOf(CardType.TREASURE)
    override val name: String = "Gold"
    override val cost: Int = 6
    override val draw: Int = 0
    override val actions: Int = 0
    override val buys: Int = 0
    override val gold: Int = 3
    override val points: Int = 0
}