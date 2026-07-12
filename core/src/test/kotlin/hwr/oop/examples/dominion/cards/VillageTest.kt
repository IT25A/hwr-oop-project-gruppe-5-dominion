package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class VillageTest: CardTest(Card.VILLAGE, 1, 2, 0, 0,0, 3) {
    @Test
    fun villagePlayTest() {
        super.playTest()
    }
}