package hwr.oop.examples.template.core

interface Card {
    val name: CardNames
    val cost: Int

    val actions: Int
    val purchases: Int

    val money: Int
    val points: Int
}
