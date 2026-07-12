package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class SmithyTest: CardTest(Card.SMITHY, 3, 0, 0, 0, 0, 4) {
    @Test
    fun smithyPlayTest() {
        super.playTest()
    }
}