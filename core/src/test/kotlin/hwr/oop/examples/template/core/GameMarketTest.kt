package hwr.oop.examples.template.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameMarketTest {

    private fun market() =
        GameMarket(setOf(Pile(Card.COPPER, 1)))

    private fun player() =
        ActivePlayer(
            Player(PlayerId("p1"), PlayerCards(hand = listOf(Card.COPPER))),
            Stats(1, 1, 10)
        )

    @Test
    fun purchase_removes_card() {
        val m = market()

        val result = m.purchase(player(), Card.COPPER)

        assertTrue(result.market.piles.isEmpty())
    }

    @Test
    fun purchase_missing_pile_throws() {
        val m = GameMarket(setOf(Pile(Card.GOLD, 1)))

        assertThrows(PurchaseException::class.java) {
            m.purchase(player(), Card.COPPER)
        }
    }

    @Test
    fun pile_draw_reduces_quantity() {
        val pile = Pile(Card.COPPER, 2)
        val m = GameMarket(setOf(pile))

        val result = m.drawFrom(pile)

        assertEquals(1, result.piles.first().count())
    }

    @Test
    fun pile_removed_when_empty() {
        val pile = Pile(Card.COPPER, 1)
        val m = GameMarket(setOf(pile))

        val result = m.drawFrom(pile)

        assertTrue(result.piles.isEmpty())
    }
}