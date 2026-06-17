package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.GameMarket
import hwr.oop.examples.template.core.PlayResult
import hwr.oop.examples.template.core.Player
import hwr.oop.examples.template.core.PlayerCards
import hwr.oop.examples.template.core.Stats
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LaboratoryTest: CardTest(Card.LABORATORY, 2, 1, 0, 0) {
    @Test
    fun laboratoryPlayTest() {
        super.playTest()
    }

}