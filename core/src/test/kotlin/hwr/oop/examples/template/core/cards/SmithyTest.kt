package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class SmithyTest: CardTest(Card.SMITHY, 3, 0, 0, 0) {
    @Test
    fun smithyPlayTest() {
        super.playTest()
    }
}