package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.dominion. testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BuyCardsCommandTest {

    private class FakeRepository(
        private var game: GameInstance
    ) : GameRepository {

        var saveCalls = 0

        override fun save(game: GameInstance) {
            saveCalls++
            this.game = game
        }

        override fun loadByid(gameId: GameID): GameInstance = game
    }

    @Test
    fun `buying no cards skips phase and saves`() {
        val game = Fixture.purchasePhaseGame()
        val repository = FakeRepository(game)

        BuyCardsCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id",
                game.currentPlayerId().value,
                "--card-name", "" //leaving this out doesn't call the command
            )

        assertThat(repository.saveCalls).isEqualTo(1)
    }

    @Test
    fun `buying a card saves game`() {
        val game = Fixture.purchasePhaseGame()
        val repository = FakeRepository(game)

        BuyCardsCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id",
                game.currentPlayerId().value,
                "--card-name",
                "copper"
            )

        assertThat(repository.saveCalls).isEqualTo(1)
    }

    @Test
    fun `invalid card prints error`() {
        val game = Fixture.purchasePhaseGame()
        val repository = FakeRepository(game)

        val result = BuyCardsCommand()
            .context {
                obj = CliContext(repository, game.id())
            }
            .test(
                "--player-id",
                game.currentPlayerId().value,
                "--card-name",
                "THIS_CARD_DOES_NOT_EXIST"
            )

        assertThat(result.stdout).contains("Couldn't buy card:")
    }
}