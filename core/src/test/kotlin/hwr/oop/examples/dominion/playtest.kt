package hwr.oop.examples.dominion

import hwr.oop.examples.dominion.testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class playtest {

    private val game = Fixture.game()


    @Test
    fun `coins remaing is returned correctly`() {
        //given
        //when
        val result = game.coinsAvailable()
        //then
        assertThat(result).isEqualTo(4)
    }

    @Test
    fun `buys remaining is sreturned correctly`() {
        //given
        //when
        val result = game.buysRemaining()
        //then
        assertThat(result).isEqualTo(3)
    }

    @Test
    fun `current phase is returned correctly`() {
        //given
        //when
        val result = game.currentPhase()
        //then
        assertThat(result).isEqualTo("ActionPhase")
    }

    @Test
    fun `current player ID is returned correctly`() {
        //given
        //when
        val result = game.currentPlayerId()
        //then
        assertThat(result).isEqualTo(PlayerId("beta"))

    }

    @Test
    fun `remaning actions are returned correctly`() {
        //given
        //when
        val result = game.actionsRemaining()
        //then
        assertThat(result).isEqualTo(2)

    }

    @Test
    fun `status is in progress while in progress`() {
        //given
        //when
        val result = game.status()
        //then
        assertThat(result).isEqualTo("IN_PROGRESS")
    }



    @Test
    fun `game id can be returned`() {
        //given
        //when
        val result = game.id()
        //then
        assertThat(result).isEqualTo(game.id())
    }
}