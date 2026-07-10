package hwr.oop.examples.template

import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import hwr.oop.examples.dominion.testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class FileSystemPersistenceTest {

	private val fakeFileSystem = FakeFileSystem()
	private val tempDir = "/tmp/template-test".toPath()
	private val sut: FileSystemPersistence

	init {
		fakeFileSystem.createDirectories(tempDir)
		sut = FileSystemPersistence(
			FileSystemPersistenceConfiguration(tempDir),
			fakeFileSystem
		)
	}

	private val game = Fixture.game()
	private val gameId = game.id()

	@Test
	fun `can store games in file system`() {
		// when
		sut.save(game)
		val loaded = sut.loadByid(gameId)

		// then
		assertThat(loaded).isEqualTo(game)
	}

	@Test
	fun `load game not saved, exception`() {
		// when / then
		assertThatThrownBy {
			sut.loadByid(gameId)
		}.hasMessageContainingAll("Could not load game", gameId.toString())
	}

	@AfterEach
	fun tearDown() {
		fakeFileSystem.checkNoOpenFiles()
	}
}

