package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class GoldTest: CardTest(Card.GOLD, 0, 0, 0, 3,0,3) {
    @Test
    fun goldPlayTest() {
        super.playTest()
    }
}