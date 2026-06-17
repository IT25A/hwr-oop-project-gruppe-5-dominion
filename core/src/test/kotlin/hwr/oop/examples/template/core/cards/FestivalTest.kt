package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class FestivalTest: CardTest(Card.FESTIVAL, 0, 2, 1, 2) {
    @Test
    fun festivalPlayTest() {
        super.playTest()
    }

}