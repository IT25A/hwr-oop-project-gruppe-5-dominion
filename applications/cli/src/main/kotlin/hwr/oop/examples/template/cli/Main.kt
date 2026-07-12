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

class BaseCommand : CliktCommand(name = "example") {
	override fun run() {
		echo("Welcome to Dominion.")
	}
}

fun main(args: Array<String>) {
	val appConfig = ConfigLoader.load()
	val persistence = buildPersistence(appConfig)
	BaseCommand()
		.subcommands(
			StartGameCommand(persistence),
			OnGameIdCommand(persistence).subcommands(
				GetGameCommand(),
				GetHandCommand(),
				SkipPhaseCommand(),
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