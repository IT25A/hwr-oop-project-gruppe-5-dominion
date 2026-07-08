package hwr.oop.examples.adapters.out

import hwr.oop.examples.core.testdata.Fixture
import hwr.oop.examples.ports.out.LoadGameByIdPort
import hwr.oop.examples.ports.out.SaveGamePort
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class StoringGamesTest {

    private lateinit var savePort: SaveGamePort
    private lateinit var loadPort: LoadGameByIdPort

    private val game = Fixture.game()
    private val gameId = game.id()

    @BeforeEach
    fun setUp() {
        val adapter = InMemoryPersistence()
        savePort = adapter
        loadPort = adapter
    }

    @Test
    fun `can store games in memory`() {
        // when
        savePort.save(game)
        val loaded = loadPort.loadByid(gameId)

        // then
        assertThat(loaded).isEqualTo(game)
    }

    @Test
    fun `load game not saved, exception`() {
        // when / then
        assertThatThrownBy {
            loadPort.loadByid(gameId)
        }.hasMessageContainingAll("Could not load game", gameId.toString())
    }
}