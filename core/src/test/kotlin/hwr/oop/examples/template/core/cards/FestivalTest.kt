package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class FestivalTest: CardTest(Card.FESTIVAL, Festival()) {
    @Test
    fun festivalPlayTest() {
        super.playTest()
    }
}