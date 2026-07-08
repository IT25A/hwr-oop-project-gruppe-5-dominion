package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class DuchyTest: CardTest(Card.DUCHY, 0, 0, 0, 0) {
    @Test
    fun duchyPlayTest() {
        super.playTest()
    }
}