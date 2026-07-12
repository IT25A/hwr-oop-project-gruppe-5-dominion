package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GameMarket
import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.Player
import hwr.oop.examples.dominion.PlayerCards
import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.dominion.Stats

import org.assertj.core.api.Assertions.assertThat

open class CardTest(val card: Card, val expectedDraws: Int,val expectedActions: Int, val expectedBuys: Int, val expectedGold: Int) {

    fun playTest() {
        val player = Player(PlayerId("player"), PlayerCards(hand = listOf(card)))
        val market = GameMarket(emptySet())
        val state = BoardState(market, emptyList())
        val result = card.play(player, Stats(0, 0, 0), state)
        require(result is GamePhase.ActiveGamePhase)
        val activePlayer = result.activePlayer

        assertThat(activePlayer.player.currentHand().size).isEqualTo(expectedDraws)
        assertThat(activePlayer.actions()).isEqualTo(expectedActions)
        assertThat(activePlayer.buys()).isEqualTo(expectedBuys)
        assertThat(activePlayer.coins()).isEqualTo(expectedGold)
    }
}