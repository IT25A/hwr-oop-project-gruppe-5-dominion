package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.parse
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StartGameCommandTest {

    private class FakeRepository : GameRepository {
        var savedGame: GameInstance? = null

        override fun save(game: GameInstance) {
            savedGame = game
        }

        override fun loadByid(gameId: GameID): GameInstance {
            error("not used")
        }
    }

    @Test
    fun `fails with fewer than 10 kingdom cards`() {
        val repository = FakeRepository()

        val exception = assertThrows<IllegalArgumentException> {
            StartGameCommand(repository).parse(
                listOf(
                    "--player-id", "p1",
                    "--player-id", "p2",
                    "--kingdom-card", "Village",
                    "--kingdom-card", "Smithy"
                )
            )
        }

        assertThat(exception.message)
            .isEqualTo("exactly 10 kingdom cards are required")

        assertThat(repository.savedGame).isNull()
    }

    @Test
    fun `fails with invalid player count`() {
        val repository = FakeRepository()

        val exception = assertThrows<IllegalArgumentException> {
            StartGameCommand(repository).parse(
                listOf(
                    "--player-id", "p1",
                    "--kingdom-card", "Village",
                    "--kingdom-card", "Smithy",
                    "--kingdom-card", "Market",
                    "--kingdom-card", "Festival",
                    "--kingdom-card", "Laboratory",
                    "--kingdom-card", "Silver",
                    "--kingdom-card", "Cellar",
                    "--kingdom-card", "Woodcutter",
                    "--kingdom-card", "Duchy",
                    "--kingdom-card", "Cellar"
                )
            )
        }

        assertThat(exception.message)
            .isEqualTo("2-4 players are required")

        assertThat(repository.savedGame).isNull()
    }

    @Test
    fun `creates and saves game`() {
        val repository = FakeRepository()

        StartGameCommand(repository).parse(
            listOf(
                "--player-id", "p1",
                "--player-id", "p2",
                "--kingdom-card", "Village",
                "--kingdom-card", "Smithy",
                "--kingdom-card", "Market",
                "--kingdom-card", "Festival",
                "--kingdom-card", "Laboratory",
                "--kingdom-card", "Silver",
                "--kingdom-card", "Cellar",
                "--kingdom-card", "Woodcutter",
                "--kingdom-card", "Duchy",
                "--kingdom-card", "Cellar"
            )
        )

        assertThat(repository.savedGame).isNotNull
    }
}