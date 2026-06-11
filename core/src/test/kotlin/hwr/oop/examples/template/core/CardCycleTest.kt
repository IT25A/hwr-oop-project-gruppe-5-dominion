package hwr.oop.examples.template.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardCycleTest {

    @Test
    fun `used cards are preserved when drawing causes reshuffle`() {
        //given
        val cards = PlayerCards(
            stock = listOf(Card.COPPER),
            discard = listOf(Card.COPPER, Card.COPPER),
            hand = listOf(Card.COPPER),
            used = listOf(Card.ESTATE)
        )
        //when
        val result = cards.draw(2)
        //then
        assertThat(result.usedSize()).isEqualTo(1)
    }

    @Test
    fun `drawing preserves total card count`() {
        val cards = PlayerCards(
            stock = List(5) { Card.COPPER },
            discard = List(5) { Card.ESTATE }
        )

        val result = cards.draw(3)

        val totalBefore = 10
        val totalAfter = result.stockSize() +
                    result.discardSize() +
                    result.handSize() +
                    result.usedSize()

        assertThat(totalAfter).isEqualTo(totalBefore)
    }

    @Test
    fun `playing a card removes it from hand`() {
        //when
        val player = ActivePlayer.create(Player("a", PlayerCards()).draw(5))
        val current = player.player.hand().size
""      //when
        val result = player.play(Card.COPPER, BoardState(GameMarket(emptySet()), emptyList())) as PlayResult.Complete
        val game = result.context.flush()
        //then
        assertThat(game.activePlayer.player.hand().size).isEqualTo(current - 1)
    }

}