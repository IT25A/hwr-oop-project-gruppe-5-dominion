package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class CopperTest: CardTest(Card.COPPER, 0, 0, 0, 1, 0, 1) {
    @Test
    fun copperPlayTest() {
        super.playTest()
    }
}