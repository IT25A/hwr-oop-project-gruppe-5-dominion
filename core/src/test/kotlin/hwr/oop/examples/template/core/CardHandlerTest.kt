package hwr.oop.examples.template.core

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertNull

class CardHandlerTest {

    @Test
    fun findCardTest(){
        //given
        val cards = listOf(Copper())
        val handler = CardHandler(cards)
        //when
        val id = CardNames.COPPER
        val card = handler.findCard(id)
        val name = card!!.name

        val wrongId = CardNames.ESTATE
        val nullCard = handler.findCard(wrongId)
        //then
        assertNotNull(card)
        assertNull(nullCard)
        assertThat(name).isEqualTo(CardNames.COPPER)
    }


}