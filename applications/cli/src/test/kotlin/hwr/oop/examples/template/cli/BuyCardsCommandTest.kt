package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.testing.test
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BuyCardsCommandTest {

    private class FakeRepository(
        private var game: GameInstance
    ) : GameRepository {

        val savedGames = mutableListOf<GameInstance>()

        override fun save(game: GameInstance) {
            savedGames += game
            this.game = game
        }

        override fun loadByid(gameId: GameID): GameInstance = game
    }

    @Test
    fun `skipping buys saves updated game`() {
        val game = createPurchasePhaseGame()
        val repo = FakeRepository(game)

        BuyCardsCommand()
            .context {
                obj = CliContext(repo, game.id())
            }
            .test("--player-id", currentPlayerId(game))

        assertThat(repo.savedGames).hasSize(1)
    }

    @Test
    fun `buying card saves updated game`() {
        val game = createPurchasePhaseGame()
        val repo = FakeRepository(game)

        BuyCardsCommand()
            .context {
                obj = CliContext(repo, game.id())
            }
            .test(
                "--player-id", currentPlayerId(game),
                "--card-name", "Copper"
            )

        assertThat(repo.savedGames).hasSize(1)
    }

    @Test
    fun `failed purchase prints error message`() {
        val game = createPurchasePhaseGame()
        val repo = FakeRepository(game)

        val result = BuyCardsCommand()
            .context {
                obj = CliContext(repo, game.id())
            }
            .test(
                "--player-id", currentPlayerId(game),
                "--card-name", "DefinitelyNotACard"
            )

        assertThat(result.stdout).contains("Couldn't buy card:")
    }
}