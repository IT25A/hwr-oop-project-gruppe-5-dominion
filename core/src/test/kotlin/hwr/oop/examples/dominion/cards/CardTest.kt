package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GameMarket
import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.Pile
import hwr.oop.examples.dominion.Player
import hwr.oop.examples.dominion.PlayerCards
import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.dominion.Stats

import org.assertj.core.api.Assertions.assertThat

open class CardTest(
    val card: Card,
    val expectedDraws: Int,
    val expectedActions: Int,
    val expectedBuys: Int,
    val expectedGold: Int,
    val expectedPoints: Int,
    val expectedPrice: Int
) {

    private val player = Player(PlayerId("player"), PlayerCards(stock = List(Int.MAX_VALUE){ Card.COPPER }, hand = listOf(card)))
    private val market = GameMarket(setOf(Pile(card, 1)))
    private val state = BoardState(market, emptyList())

    fun playTest() {
        val result = card.play(player, Stats(0, 0, 0), state)
        require(result is GamePhase.ActiveGamePhase)
        val activePlayer = result.activePlayer
        var expectedActions = expectedActions
        if(card.isAction()){
            expectedActions--
        }
        assertThat(activePlayer.player.currentHand().size).isEqualTo(expectedDraws)
        assertThat(activePlayer.actions()).isEqualTo(expectedActions)
        assertThat(activePlayer.buys()).isEqualTo(expectedBuys)
        assertThat(activePlayer.coins()).isEqualTo(expectedGold)
        assertThat(result.currentPlayersPoints()).isEqualTo(expectedPoints)
    }

    fun purchaseTest() {
        val money = Int.MAX_VALUE
        val player = ActivePlayer(player, Stats(0, 1, money))
        val result = market.purchase(player, card).player.coins()
        assertThat(result).isEqualTo(money - expectedPrice)
    }
}