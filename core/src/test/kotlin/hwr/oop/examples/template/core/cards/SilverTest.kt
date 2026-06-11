package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class SilverTest: CardTest(Card.SILVER, Silver()) {
    @Test
    fun silverPlayTest() {
        super.playTest()
    }
}