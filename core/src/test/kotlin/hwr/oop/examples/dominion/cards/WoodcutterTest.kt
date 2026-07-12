package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class WoodcutterTest: CardTest(Card.WOODCUTTER, 0, 0, 1, 2,0,3) {
    @Test
    fun woodcutterPlayTest() {
        super.playTest()
    }
}