package hwr.oop.examples.dominion

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardTest {

    @Test
    fun createCardTest(){
        //given
        val cardDefinition = object: CardDefinition{
            override val types: List<CardType> = listOf(CardType.TREASURE)
            override val name: String = "CardDefinition"
            override val cost: Int = 3

            override val draw: Int = 0
            override val actions: Int = 2
            override val buys: Int = 0

            override val gold: Int = 0
            override val points: Int = 0
        }

        //
        val name = cardDefinition.name
        val cost = cardDefinition.cost
        val additionalActions = cardDefinition.actions
        val additionalPurchases = cardDefinition.buys
        val additionalMoney = cardDefinition.gold
        val additionalPoints = cardDefinition.points
        //then
        assertThat(name).isEqualTo("CardDefinition")
        assertThat(cost).isEqualTo(3)
        assertThat(additionalActions).isEqualTo(2)
        assertThat(additionalPurchases).isEqualTo(0)
        assertThat(additionalMoney).isEqualTo(0)
        assertThat(additionalPoints).isEqualTo(0)
    }

}