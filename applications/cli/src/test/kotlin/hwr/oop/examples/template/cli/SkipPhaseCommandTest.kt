package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.dominion.testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SkipPhaseCommandTest {

    private class FakeRepository(
        private val game: GameInstance
    ) : GameRepository {

        var savedGame: GameInstance? = null

        override fun save(game: GameInstance) {
            savedGame = game
        }

        override fun loadByid(gameId: GameID): GameInstance {
            return game
        }
    }

    @Test
    fun `skips phase and saves game`() {
        val game = Fixture.actionPhaseGame()
        val repository = FakeRepository(game)

        SkipPhaseCommand()
            .context {
                obj = CliContext(
                    repository,
                    game.id()
                )
            }
            .test()

        assertThat(repository.savedGame).isNotNull
        assertThat(repository.savedGame?.currentPhase()).isNotEqualTo(game.currentPhase())
    }
}