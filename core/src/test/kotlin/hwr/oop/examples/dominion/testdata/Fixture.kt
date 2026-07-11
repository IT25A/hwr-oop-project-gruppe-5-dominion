package hwr.oop.examples.dominion.testdata

import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.GameMarket
import hwr.oop.examples.dominion.PlayerCards

object Fixture {

    val alpha = PlayerId("alpha")
    val beta = PlayerId("beta")
    val gamma = PlayerId("gamma")
    val delta = PlayerId("delta")


    val PlayerCards = listOf(
        alpha =

    )

            


//    fun game(): Game = Game(
//        handsOfPlayers = hands,
//        bouts = pastBoutsFinished,
//    )

    private infix fun PlayerId.withHand(cards: List<Card>): Hand = Hand(this, cards)
}