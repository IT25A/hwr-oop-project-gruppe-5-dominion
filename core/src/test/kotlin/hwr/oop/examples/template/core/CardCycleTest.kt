package hwr.oop.examples.template.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CardCycleTest {

    @Test
    fun `draw zero returns same`() {
        val cards = PlayerCards()
        assertSame(cards, cards.draw(0))
    }

    @Test
    fun `draw from stock`() {
        val cards = PlayerCards(
            stock = listOf(Card.COPPER, Card.COPPER, Card.ESTATE)
        )

        val result = cards.draw(2)

        assertEquals(2, result.handSize())
        assertEquals(1, result.stockSize())
    }

    @Test
    fun `draw more than stock triggers logic`() {
        val cards = PlayerCards(
            stock = listOf(Card.COPPER),
            discard = listOf(Card.ESTATE, Card.SILVER)
        )

        val result = cards.draw(2)

        assertTrue(result.handSize() >= 1)
    }

    @Test
    fun `draw exhausts all cards`() {
        val cards = PlayerCards(
            stock = listOf(Card.COPPER),
            discard = listOf(Card.ESTATE)
        )

        val result = cards.draw(10)

        assertEquals(2, result.handSize())
        assertEquals(0, result.stockSize())
        assertEquals(0, result.discardSize())
    }

    @Test
    fun `use moves card to used`() {
        val cards = PlayerCards(hand = listOf(Card.COPPER))

        val result = cards.use(Card.COPPER)

        assertTrue(result.hand.isEmpty())
        assertEquals(1, result.usedSize())
    }

    @Test
    fun `use invalid throws`() {
        val cards = PlayerCards()

        assertThrows(IllegalArgumentException::class.java) {
            cards.use(Card.COPPER)
        }
    }

    @Test
    fun `insert adds to used`() {
        val cards = PlayerCards(hand = listOf(Card.COPPER))
        val result = cards.insert(Card.GOLD)

        assertEquals(1, result.usedSize())
    }

    @Test
    fun `end turn moves everything except stock to discard`() {
        val cards = PlayerCards(
            stock = listOf(Card.COPPER),
            hand = listOf(Card.SILVER),
            used = listOf(Card.GOLD),
            discard = listOf(Card.ESTATE)
        )

        val result = cards.endTurn()

        assertEquals(3, result.discardSize())
        assertEquals(1, result.stockSize())
        assertEquals(0, result.handSize())
        assertEquals(0, result.usedSize())
    }

}