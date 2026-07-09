package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.CardDefinition
import hwr.oop.examples.dominion.CardType

class Estate: CardDefinition {
    override val types: List<CardType> = listOf(CardType.POINTS)
    override val name: String = "estate"
    override val cost: Int = 2
    override val draw: Int = 0
    override val actions: Int = 0
    override val buys: Int = 0
    override val gold: Int = 0
    override val points: Int = 1

    //override fun specialTextApplication(gameState: BoardState): BoardState = gameState
}