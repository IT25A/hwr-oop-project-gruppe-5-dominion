package hwr.oop.examples.template.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class playCardsTest {

    val players = listOf(Player("1", PlayerCards(hand = List(5){Card.COPPER})))
    val gamecontext = GameContext(ActivePlayer(players[0], Stats(0, 0, 0)), BoardState(Market(setOf(Pile(Card.COPPER, 1)), 0), players))

    @Test
    fun `playing a card puts it in used area`() {
        //given
        val hand = List(1){Card.COPPER}
        val cards = PlayerCards(hand = hand)
        //when
        val newCards = cards.use(Card.COPPER)
        //then
        assertThat(newCards.hand).hasSize(0)
        assertThat(newCards.used).hasSize(1)
    }

    @Test
    fun `playing a card`() {
        //when
        val result = gamecontext.player().play(Card.COPPER, gamecontext.state())
        //then
        if (result is PlayResult.Complete) {
            assertThat(result.context.player().player.cards.used).containsExactly(Card.COPPER)
        }else{
            fail { "should not be choice" }
        }
    }
}