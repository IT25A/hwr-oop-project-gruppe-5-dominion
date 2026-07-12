import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.template.cli.CliContext
import hwr.oop.examples.template.cli.GetHandCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetHandCommandTest {

    @Test
    fun `prints current players hand`() {
        val game = GameInstance.create(
            playerIds = listOf("p1", "p2"),
            kingdomCards = listOf(
                "Village",
                "Smithy",
                "Market",
                "Festival",
                "Laboratory",
                "Silver",
                "Cellar",
                "Duchy",
                "Woodcutter",
                "Estate"
            )
        )

        val repository = object : GameRepository {
            override fun save(game: GameInstance) = Unit

            override fun loadByid(gameId: GameID): GameInstance = game
        }

        val ctx = CliContext(
            persistence = repository,
            gameId = game.id()
        )

        val result = GetHandCommand()
            .context {
                obj = ctx
            }
            .test()

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(result.stdout).contains("Hand")

        game.currentPlayersHand().forEach {
            assertThat(result.stdout).contains(it.name)
        }
    }
}