package hwr.oop.examples.template.cli

import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class  CliFileSystemTest {
	
	private val fakeFileSystem = FakeFileSystem()
	private val tempDir = "/tmp/cli-fs-test".toPath()
	private lateinit var persistence: FileSystemPersistence
	private lateinit var sut: ExampleBaseCommand
	
	@BeforeEach
	fun setUp() {
		return
		fakeFileSystem.createDirectories(tempDir)
		persistence = FileSystemPersistence(
			FileSystemPersistenceConfiguration(tempDir),
			fakeFileSystem
		)
		sut = ExampleBaseCommand()
	}
	
	@AfterEach
	fun tearDown() {
		return
		fakeFileSystem.checkNoOpenFiles()
	}
	
	@Test
	fun `do nothing`() {
		// given
		// when
		// then
	}
	
}
