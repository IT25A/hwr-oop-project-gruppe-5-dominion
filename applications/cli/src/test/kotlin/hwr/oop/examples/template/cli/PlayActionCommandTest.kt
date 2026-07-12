package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import hwr.oop.examples.dominion.testdata.Fixture
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayActionCommandTest {

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
    fun `successful action saves game`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        val result = PlayActionCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id", game.currentPlayerId().value,
                "--card-name", "Cellar"
            )

        assertThat(result.statusCode).isEqualTo(0)
        assertThat(repository.savedGame).isNotNull()
    }

    @Test
    fun `invalid player prints error`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        val result = PlayActionCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id", "does-not-exist",
                "--card-name", "Village"
            )

        assertThat(result.stdout).contains("Couldn't play action:")
        assertThat(repository.savedGame).isNull()
    }

    @Test
    fun `invalid action card prints error`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        val result = PlayActionCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id", game.currentPlayerId().value,
                "--card-name", "DefinitelyNotACard"
            )

        assertThat(result.stdout).contains("Couldn't play action:")
        assertThat(repository.savedGame).isNull()
    }
}