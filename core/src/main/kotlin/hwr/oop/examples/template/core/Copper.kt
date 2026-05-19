package hwr.oop.examples.template.core

class Copper: Card {
    override val name: CardNames = CardNames.COPPER
    override val cost: Int = 0

    override val actions: Int = 0
    override val purchases: Int = 0

    override val money: Int = 2
    override val points: Int = 0
}
