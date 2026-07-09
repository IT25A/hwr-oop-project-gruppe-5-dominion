package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class SilverTest: CardTest(Card.SILVER, 0, 0, 0, 2) {
    @Test
    fun silverPlayTest() {
        super.playTest()
    }
}