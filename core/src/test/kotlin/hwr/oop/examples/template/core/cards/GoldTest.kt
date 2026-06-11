package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class GoldTest: CardTest(Card.GOLD, Gold()) {
    @Test
    fun goldPlayTest() {
        super.playTest()
    }
}