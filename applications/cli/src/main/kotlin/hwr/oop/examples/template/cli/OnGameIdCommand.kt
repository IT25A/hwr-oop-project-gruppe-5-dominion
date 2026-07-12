package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.obj
import com.github.ajalt.clikt.parameters.arguments.argument
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.ports.out.GameRepository

class OnGameIdCommand(
	private val persistence: GameRepository
) : CliktCommand(name = "onGameID") {
	private val gameId by argument("GAMEID", help = "The unique identifier of the game.")

	override fun run() {
		currentContext.obj = CliContext(persistence, GameID(gameId))
	}
}
