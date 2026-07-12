package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.AnsweredChoice
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.GameContext
import hwr.oop.examples.dominion.GameMarket
import hwr.oop.examples.dominion.Player
import hwr.oop.examples.dominion.PlayerCards
import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.dominion.Stats
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat


class CellarTest: CardTest(Card.CELLAR, 0, 1, 0, 0) {

    @Test
    fun cellarPlayTest() {
        super.playTest()
    }

    @Test
    fun cellarEffectTest(){
        val state = BoardState(GameMarket(emptySet()), emptyList())
        val id = PlayerId("p1")
        val player = Player(
            id,
            PlayerCards(stock = listOf(Card.ESTATE), hand = listOf(Card.CELLAR, Card.COPPER, Card.COPPER)),
        )
        val stats = Stats(0, 0, 0)
        val ctx = GameContext(player, stats, state)

        val result1 = Card.CELLAR.play(player, stats, state)

        assertThat(result1).isInstanceOf(GamePhase.PendingEffectPhase::class.java)
        assertThat((result1 as GamePhase.PendingEffectPhase).pending().size).isEqualTo(1)

        val result2 = result1.answer(AnsweredChoice(id, listOf("1")))

        assertThat(result2).isInstanceOf(GamePhase.PendingEffectPhase::class.java)
        assertThat((result2 as GamePhase.PendingEffectPhase).pending().size).isEqualTo(1)

        val result3 = result2.answer(AnsweredChoice(id, listOf("Copper")))
        require(result3 is GamePhase.ActiveGamePhase)

        assertThat(result3).isInstanceOf(GamePhase.ActionPhase::class.java)
        assertThat(result3.activePlayer.player.cards.hand).isEqualTo(listOf(Card.COPPER, Card.ESTATE))
        assertThat(result3.activePlayer.player.cards.discard).contains(Card.COPPER)
    }


}