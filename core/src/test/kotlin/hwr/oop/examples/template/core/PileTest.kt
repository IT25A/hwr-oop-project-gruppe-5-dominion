package hwr.oop.examples.template.core

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PileTest {

    @Test
    fun `equality ignores quantity`() {
        Assertions.assertEquals(
            Pile(Card.COPPER, 1),
            Pile(Card.COPPER, 999)
        )
    }

    @Test
    fun `draw decreases count`() {
        val p = Pile(Card.COPPER, 5)
        Assertions.assertEquals(4, p.draw().count())
    }

    @Test
    fun `empty check`() {
        Assertions.assertTrue(Pile(Card.COPPER, 0).isEmpty())
    }
}