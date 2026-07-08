package hwr.oop.examples.dominion.cards

import hwr.oop.examples.dominion.Card
import org.junit.Test

class MarketTest : CardTest(Card.MARKET, 1, 1, 1, 1) {
    @Test
    fun marketPlayTest() {
        super.playTest()
    }

}