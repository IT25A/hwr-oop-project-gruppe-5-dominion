package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class CopperTest: CardTest(Card.COPPER, Copper()) {
    @Test
    fun copperPlayTest() {
        super.playTest()
    }
}