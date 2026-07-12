package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.dominion.testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetGameCommandTest {

    private class FakeRepository(
        private val game: GameInstance
    ) : GameRepository {

        override fun save(game: GameInstance) = Unit

        override fun loadByid(gameId: GameID): GameInstance = game
    }

    @Test
    fun `prints active game information`() {
        val game = GameInstance.create(
            listOf("p1", "p2"),
            listOf(
                "Village",
                "Smithy",
                "Market",
                "Festival",
                "Laboratory",
                "Silver",
                "Cellar",
                "Woodcutter",
                "Estate",
                "Duchy"
            )
        )

        val result = GetGameCommand()
            .context {
                obj = CliContext(
                    FakeRepository(game),
                    game.id()
                )
            }
            .test()

        assertThat(result.stdout).contains("=== Game")
        assertThat(result.stdout).contains("Phase:")
        assertThat(result.stdout).contains(">Players:")
        assertThat(result.stdout).contains(">Market:")
        assertThat(result.stdout).contains(">Current Player:")
    }

    @Test
    fun `prints winner when game is concluded`() {
        val finishedGame = Fixture.finishedGame()

        val result = GetGameCommand()
            .context {
                obj = CliContext(
                    FakeRepository(finishedGame),
                    finishedGame.id()
                )
            }
            .test()

        assertThat(result.stdout).contains("Game concluded")
        assertThat(result.stdout).contains("Winner:")
    }
}