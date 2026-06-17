package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.ActivePlayer
import hwr.oop.examples.template.core.AnsweredChoice
import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.Game
import hwr.oop.examples.template.core.GameContext
import hwr.oop.examples.template.core.GameMarket
import hwr.oop.examples.template.core.Player
import hwr.oop.examples.template.core.PlayerCards
import hwr.oop.examples.template.core.PlayerId
import hwr.oop.examples.template.core.Stats
import hwr.oop.examples.template.core.containsAllCopiesOf
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
        val player = Player(id, PlayerCards(stock = listOf(Card.ESTATE), hand = listOf(Card.CELLAR, Card.COPPER, Card.COPPER)))
        val stats = Stats(0, 0, 0)
        val ctx = GameContext(player, stats, state)

        val result1 = Card.CELLAR.play(player, stats, state)

        assertThat(result1).isInstanceOf(Game.EffectActive::class.java)
        assertThat(result1.pending().size).isEqualTo(1)

        val result2 = result1.answer(AnsweredChoice(id, listOf("1")))

        assertThat(result2).isInstanceOf(Game.EffectActive::class.java)
        assertThat(result2.pending().size).isEqualTo(1)

        val result3 = result2.answer(AnsweredChoice(id, listOf("Copper")))

        assertThat(result3).isInstanceOf(Game.InActionPhase::class.java)
        assertThat(result3.activePlayer.player.cards.hand).isEqualTo(listOf(Card.COPPER, Card.ESTATE))
        assertThat(result3.activePlayer.player.cards.discard).contains(Card.COPPER)
    }


}