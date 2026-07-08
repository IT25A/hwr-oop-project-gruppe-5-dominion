package hwr.oop.examples.template.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DrawTest {
    @Test
    fun `Drawing a card adds it to hand`() {
        //given
        val deck = List(7){ Card.COPPER } + List(3){ Card.ESTATE }
        val cards = PlayerCards(deck)
        //when
        val newCards = cards.draw(5)
        //then
        assertThat(newCards.hand).hasSize(5)
    }

    @Test
    fun `Cards get discarded at the end of turn`() {
        //given
        val deck = emptyList<Card>()
        val hand = List(5){Card.COPPER}
        val used = List(5){Card.ESTATE}
        val cards = PlayerCards(deck, hand = hand, used = used)
        //when
        val newCards = cards.discard()
        //then
        assertThat(newCards.hand).hasSize(0)
        assertThat(newCards.used).hasSize(0)
        assertThat(newCards.discard).hasSize(10)
    }

    @Test
    fun `drawing with an empty deck reshuffels discard`() {
        //given
        val deck = emptyList<Card>()
        val discard = List(5){Card.COPPER}
        val hand = List(5){Card.ESTATE}
        val used = List(5){Card.ESTATE}
        val cards = PlayerCards(deck, discard = discard, hand = hand, used = used)
        //when
        val newCards = cards.draw(1)
        //then
        assertThat(newCards.stock).hasSize(4)
        assertThat(newCards.discard).hasSize(0)
        assertThat(newCards.hand).hasSize(6)
        assertThat(newCards.used).hasSize(5)
    }

    @Test
    fun `drawing 0 cards has no effect`() {
        //given
        val cards = PlayerCards()
        //when
        val newCards =cards.draw(0)
        //then
        assertThat(newCards.stock).hasSize(10)
        assertThat(newCards.discard).hasSize(0)
        assertThat(newCards.hand).hasSize(0)
        assertThat(newCards.used).hasSize(0)
    }

    @Test
    fun `drawing cards with not enough in discard draws as many as possible`() {
        //given
        val discard = List(5){Card.COPPER}
        val cards = PlayerCards(discard = discard)
        //when
        val newCards = cards.draw(20)
        //then
        assertThat(newCards.stock).hasSize(0)
        assertThat(newCards.discard).hasSize(0)
        assertThat(newCards.hand).hasSize(15)
        assertThat(newCards.used).hasSize(0)
    }
}