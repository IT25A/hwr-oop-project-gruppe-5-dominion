package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import hwr.oop.examples.template.SqlPersistence
import hwr.oop.examples.template.config.AppConfig
import hwr.oop.examples.template.config.ConfigLoader
import hwr.oop.examples.template.config.PersistenceType
import hwr.oop.examples.template.core.DominionPersistence
import okio.Path.Companion.toPath

class ExampleBaseCommand : CliktCommand(name = "example") {
	override fun run() = Unit
}

fun main(args: Array<String>) {
	val appConfig = ConfigLoader.load()
	val persistence = buildPersistence(appConfig)
	ExampleBaseCommand()
		.subcommands(
			StartGameCommand(),
			OnGameIdCommand().subcommands(
				GetGameCommand(),
				PlayActionCommand(),
				PlayTreasuresCommand(),
				BuyCardsCommand(),
				GetChoicesCommand(),
				MakeChoiceCommand(),
			),
		)
		.main(args)
}

private fun buildPersistence(appConfig: AppConfig): DominionPersistence {
	return when (appConfig.persistence) {
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

