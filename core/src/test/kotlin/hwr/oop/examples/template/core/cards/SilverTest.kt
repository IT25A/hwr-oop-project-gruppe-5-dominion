package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class SilverTest: CardTest(Card.SILVER, 0, 0, 0, 2) {
    @Test
    fun silverPlayTest() {
        super.playTest()
    }
}