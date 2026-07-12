package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class LaboratoryTest: CardTest(Card.LABORATORY, 2, 1, 0, 0, 0,5) {
    @Test
    fun laboratoryPlayTest() {
        super.playTest()
    }

}