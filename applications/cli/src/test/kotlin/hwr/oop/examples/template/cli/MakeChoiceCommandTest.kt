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

class MakeChoiceCommandTest {

    private class FakeRepository(
        private var game: GameInstance
    ) : GameRepository {

        var savedGame: GameInstance? = null

        override fun save(game: GameInstance) {
            savedGame = game
            this.game = game
        }

        override fun loadByid(gameId: GameID): GameInstance = game
    }

    @Test
    fun `successful choice saves game`() {
        val game = Fixture.activeEffectPhaseGame()
        val repository = FakeRepository(game)

        val playerId = game.choices().first().playerId.value

        val result = MakeChoiceCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id", playerId,
                "--selected-option", "0"
            )

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(repository.savedGame).isNotNull()
    }

    @Test
    fun `invalid player prints error`() {
        val game = Fixture.activeEffectPhaseGame()
        val repository = FakeRepository(game)

        val result = MakeChoiceCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id", "does-not-exist",
                "--selected-option", "0"
            )

        assertThat(result.stdout).contains("couldn't make choice")
        assertThat(repository.savedGame).isNull()
    }

    @Test
    fun `invalid option index prints error`() {
        val game = Fixture.activeEffectPhaseGame()
        val repository = FakeRepository(game)

        val playerId = game.choices().first().playerId.value

        val result = MakeChoiceCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id", playerId,
                "--selected-option", "999"
            )

        assertThat(result.stdout).contains("couldn't make choice")
        assertThat(repository.savedGame).isNull()
    }
}