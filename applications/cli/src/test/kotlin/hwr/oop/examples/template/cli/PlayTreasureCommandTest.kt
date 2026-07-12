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

class PlayTreasuresCommandTest {

    private class FakeRepository(
        private val game: GameInstance
    ) : GameRepository {

        override fun save(game: GameInstance) {
            error("save should not be called")
        }

        override fun loadByid(gameId: GameID): GameInstance = game
    }


    @Test
    fun `playing treasures succeeds`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        val result = PlayTreasuresCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id",
                game.currentPlayerId().value,
                "--card-name",
                "Copper"
            )

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(result.stdout).isBlank()
    }


    @Test
    fun `invalid player prints error`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        val result = PlayTreasuresCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id",
                "invalid-player",
                "--card-name",
                "Copper"
            )

        assertThat(result.stdout)
            .contains("couldn't play all treasures")
    }


    @Test
    fun `invalid treasure prints error`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        val result = PlayTreasuresCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id",
                game.currentPlayerId().value,
                "--card-name",
                "NotATreasure"
            )

        assertThat(result.stdout)
            .contains("couldn't play all treasures")
    }
}