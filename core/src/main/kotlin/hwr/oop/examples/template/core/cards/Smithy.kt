package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.CardDefinition
import hwr.oop.examples.template.core.CardType

class Smithy: CardDefinition {
    override val types: List<CardType> = listOf(CardType.ACTION)
    override val name: String = "Smithy"
    override val cost: Int = 4
    override val draw: Int = 3
    override val actions: Int = 0
    override val buys: Int = 0
    override val gold: Int = 0
    override val points: Int = 0
}