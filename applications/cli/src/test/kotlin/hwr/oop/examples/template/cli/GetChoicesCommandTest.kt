package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.dominion.testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetChoicesCommandTest {

    private class FakeRepository(
        private val game: GameInstance
    ) : GameRepository {

        override fun save(game: GameInstance) = Unit

        override fun loadByid(gameId: GameID): GameInstance = game
    }

    @Test
    fun `error when there are no pending choices`() {
        val game = Fixture.actionPhaseGame()

        val result = GetChoicesCommand()
            .context {
                obj = CliContext(
                    FakeRepository(game),
                    game.id()
                )
            }
            .test()

        assertThat(result.stdout).contains("no effect active")
    }

    @Test
    fun `prints pending choices`() {
        val game = Fixture.activeEffectPhaseGame()

        val result = GetChoicesCommand()
            .context {
                obj = CliContext(
                    FakeRepository(game),
                    game.id()
                )
            }
            .test()

        val choice = game.choices().first()

        assertThat(result.stdout).contains("=== Choice 1 ===")
        assertThat(result.stdout).contains(choice.playerId.value)
        assertThat(result.stdout).contains(choice.choiceType)
        assertThat(result.stdout).contains(choice.description)

        choice.options.forEach {
            assertThat(result.stdout).contains(it)
        }
    }
}