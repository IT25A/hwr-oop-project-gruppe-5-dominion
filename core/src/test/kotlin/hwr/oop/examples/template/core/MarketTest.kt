package hwr.oop.examples.template.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MarketTest {

    val richPlayer = ActivePlayer(Player("richPlayer", PlayerCards()), Stats(0, 2, 10))
    val poorPlayer = ActivePlayer(Player("poorPlayer", PlayerCards()), Stats(0, 1, 0))
    val sadPlayer = ActivePlayer(Player("sadPlayer", PlayerCards()), Stats(0, 0, 20))
    val market = Market(setOf(Pile(Card.COPPER, 1), Pile(Card.ESTATE, 3)))

    @Test
    fun `only existing cards can be bought`() {
        //when
        assertThatThrownBy{
            market.purchase(richPlayer, Card.TEST)
        }
            .isInstanceOf(PurchaseException::class.java)
            .hasMessageContaining("no such pile")
    }

    @Test
    fun `can't buy cards if no buys remain`() {
        assertThatThrownBy{
            market.purchase(sadPlayer, Card.COPPER)
        }
            .isInstanceOf(PurchaseException::class.java)
            .hasMessageContaining("not enough buys")
    }

    @Test
    fun `can't buy cards if they are too expensive`() {
        assertThatThrownBy{
            market.purchase(poorPlayer, Card.ESTATE)
        }
            .isInstanceOf(PurchaseException::class.java)
            .hasMessageContaining("not enough money")
    }

    @Test
    fun `buying a card reduces cards avalible`() {
        //when
        val result = market.purchase(richPlayer, Card.COPPER)
        //then
        assertThat(result.market.piles{it == Card.COPPER}.size).isEqualTo(0)
    }

    @Test
    fun `buying a cards adds it to the players discard`() {
        //when
        val result = market.purchase(richPlayer, Card.COPPER)
        //then
        assertThat(result.player.player.cards.used)
            .hasSize(1)
            .containsExactly(Card.COPPER)
    }

    @Test
    fun `buying a card reduces avalible purchases`() {
        //when
        val result = market.purchase(richPlayer, Card.COPPER)
        //then
        assertThat(result.player.stats.purchases).isEqualTo(richPlayer.stats.purchases - 1)
    }

    @Test
    fun `buying a card reduces avalible money`() {
        //when
        val result = market.purchase(richPlayer, Card.ESTATE)
        //then
        assertThat(result.player.stats.money).isEqualTo(richPlayer.stats.money - Card.ESTATE.cost())
    }
}