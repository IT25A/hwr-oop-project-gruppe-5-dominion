package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import hwr.oop.examples.template.config.AppConfig
import hwr.oop.examples.template.config.ConfigLoader
import hwr.oop.examples.template.config.PersistenceType
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

private fun buildPersistence(appConfig: AppConfig): GameRepository {
	require(appConfig.persistence == PersistenceType.FILE_SYSTEM) { "only file system persistence is supported" }
	return FileSystemPersistence(
		configuration = FileSystemPersistenceConfiguration(
			directory = appConfig.fileSystem.directory.toPath()
		)
	)

}