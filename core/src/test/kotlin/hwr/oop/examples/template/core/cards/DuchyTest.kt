package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class DuchyTest: CardTest(Card.DUCHY, 5, 0, 0, 0) {
    @Test
    fun duchyPlayTest() {
        super.playTest()
    }
}