package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.GameMarket
import hwr.oop.examples.template.core.Player
import hwr.oop.examples.template.core.PlayerCards
import hwr.oop.examples.template.core.PlayerId
import hwr.oop.examples.template.core.Stats

import org.assertj.core.api.Assertions.assertThat

open class CardTest(val card: Card, val expectedDraws: Int,val expectedActions: Int, val expectedBuys: Int, val expectedGold: Int) {

    fun playTest() {
        val player = Player(PlayerId(""), PlayerCards(PlayerCards.defaultInitialStock, emptyList(), listOf(card)))
        val market = GameMarket(emptySet())
        val state = BoardState(market, emptyList())
        val result = card.play(player, Stats(0, 0, 0), state)
        val activePlayer = result.activePlayer

        assertThat(activePlayer.player.currentHand().size).isEqualTo(expectedDraws)
        assertThat(activePlayer.actions()).isEqualTo(expectedActions)
        assertThat(activePlayer.buys()).isEqualTo(expectedBuys)
        assertThat(activePlayer.coins()).isEqualTo(expectedGold)
    }
}