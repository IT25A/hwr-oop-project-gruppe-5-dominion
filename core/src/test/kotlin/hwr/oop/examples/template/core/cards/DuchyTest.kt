package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class DuchyTest: CardTest(Card.DUCHY, Duchy()) {
    @Test
    fun duchyPlayTest() {
        super.playTest()
    }
}