package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import org.junit.Test

class VillageTest: CardTest(Card.VILLAGE, Village()) {
    @Test
    fun villagePlayTest() {
        super.playTest()
    }
}