package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class FestivalTest: CardTest(Card.FESTIVAL, 0, 2, 1, 2) {
    @Test
    fun festivalPlayTest() {
        super.playTest()
    }

}