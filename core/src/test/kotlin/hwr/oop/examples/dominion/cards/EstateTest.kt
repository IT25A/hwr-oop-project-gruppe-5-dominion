package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class EstateTest : CardTest(Card.ESTATE, 0, 0, 0, 0,1,2)  {
    @Test
    fun estatePlayTest() {
        super.playTest()
    }
}