package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.GameRepository
import hwr.oop.examples.template.FileSystemPersistence

class StartGameCommand(
	private val persistence: GameRepository
) : CliktCommand(name = "startGame") {
	private val playerIds by option(
		"--player-id",
		help = "ID of a player joining the game. Pass multiple times for each player (2–4 total)."
	).multiple(required = true)
	
	private val kingdomCards by option(
		"--kingdom-card",
		help = "Name of a kingdom card to include in the supply. Pass exactly 10 times."
	).multiple(required = true)
	
	override fun run() {
		require(kingdomCards.size == 10){ "exactly 10 kingdom cards are required" }
		require(playerIds.size in 2..4){ "2-4 players are required" }

		val game = GameInstance.create(playerIds, kingdomCards)
		persistence.save(game)
		echo("Create game with ID: ${game.id().value}")
	}
}
