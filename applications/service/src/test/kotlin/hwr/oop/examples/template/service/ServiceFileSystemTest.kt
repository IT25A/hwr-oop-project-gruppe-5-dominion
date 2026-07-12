package hwr.oop.examples.template.service

import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = MOCK)
class ServiceFileSystemTest {

	@TestConfiguration
	class Config {
		private val fakeFileSystem = FakeFileSystem()
		private val tempDir = "/tmp/service-fs-test".toPath()
		private val persistence = FileSystemPersistence(
			FileSystemPersistenceConfiguration(tempDir),
			fakeFileSystem.also { it.createDirectories(tempDir) }
		)

		@Bean("testPersistence")
		@Primary
		fun persistence(): GameRepository = persistence
	}

	@Autowired
	private lateinit var webApplicationContext: WebApplicationContext

	private lateinit var mockMvc: MockMvc

	@BeforeEach
	fun setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
	}
}
