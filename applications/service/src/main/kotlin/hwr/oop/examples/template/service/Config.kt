package hwr.oop.examples.template.service

import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import hwr.oop.examples.template.config.ConfigLoader
import hwr.oop.examples.template.config.PersistenceType
import okio.Path.Companion.toPath
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
	
	private val appConfig = ConfigLoader.load()
	private val persistence: GameRepository by lazy {
		FileSystemPersistence(
			configuration = FileSystemPersistenceConfiguration(
				directory = appConfig.fileSystem.directory.toPath()
			)
		)
	}
	
	@Bean
	@ConditionalOnMissingBean
	fun persistence(): GameRepository = persistence
}