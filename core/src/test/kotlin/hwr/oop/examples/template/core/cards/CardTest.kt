package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.CardDefinition
import hwr.oop.examples.template.core.GameMarket
import hwr.oop.examples.template.core.PlayResult
import hwr.oop.examples.template.core.Player
import hwr.oop.examples.template.core.PlayerCards
import hwr.oop.examples.template.core.Stats

import org.assertj.core.api.Assertions.assertThat

open class CardTest(val card: Card, val actual: CardDefinition) {

    fun playTest() {
        val player = Player("", PlayerCards(PlayerCards.defaultInitialStock, emptyList(), listOf(card)))
        val market = GameMarket(emptySet())
        val state = BoardState(market, emptyList())
        val result = card.play(player, Stats(0, 0, 0), state)
        require(result is PlayResult.Complete)
        val game = result.context.flush()
        val activePlayer = game.activePlayer

        // assertThat(activePlayer.stats.).isEqualTo(actual.cost)
        assertThat(activePlayer.player.hand().size).isEqualTo(actual.draw)
        assertThat(activePlayer.purchases()).isEqualTo(actual.buys)
        assertThat(activePlayer.coins()).isEqualTo(actual.gold)
        // assertThat(activePlayer.)isEqualTo(actual.points)
    }
}