package hwr.oop.examples.dominion.Exceptions
import hwr.oop.examples.dominion.NoPendingChoiceException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

val playerID = "Player1"
class NoPendingChoiseTest {
    @Test
    fun `no pending choise is available`() {
        try{
            throw NoPendingChoiceException(playerID)
        }
        catch (ex: NoPendingChoiceException){

            assertThat(ex.message).isEqualTo("not waiting for choice of player: $playerID")
        }
    }
}