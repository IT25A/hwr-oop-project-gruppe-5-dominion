package hwr.oop.examples.template.service

import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import hwr.oop.examples.template.SqlPersistence
import hwr.oop.examples.template.config.ConfigLoader
import hwr.oop.examples.template.config.PersistenceType
import okio.Path.Companion.toPath
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {
	
	private val appConfig = ConfigLoader.load()
	private val persistence: hwr.oop.examples.dominion.DominionPersistence by lazy {
		when (appConfig.persistence) {
			PersistenceType.SQL -> SqlPersistence(
				appConfig.sql.jdbcUrl,
				appConfig.sql.username,
				appConfig.sql.password,
			)
			
			PersistenceType.FILE_SYSTEM -> FileSystemPersistence(
				configuration = FileSystemPersistenceConfiguration(
					directory = appConfig.fileSystem.directory.toPath()
				)
			)
		}
	}
	
	@Bean
	@ConditionalOnMissingBean
	fun persistence(): DominionPersistence = persistence
}