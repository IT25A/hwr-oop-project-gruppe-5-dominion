package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class WoodcutterTest: CardTest(Card.WOODCUTTER, 0, 0, 1, 2) {
    @Test
    fun woodcutterPlayTest() {
        super.playTest()
    }
}