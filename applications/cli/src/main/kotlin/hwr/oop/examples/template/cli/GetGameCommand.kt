package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import hwr.oop.examples.dominion.GameInstance

class GetGameCommand : CliktCommand(name = "getGame") {
	private val ctx by requireObject<CliContext>()

	private lateinit var instance: GameInstance

	override fun run() {
		instance = ctx.loadGame()

		if(instance.status() == "IN_PROGRESS"){
			activeGame()
		} else {
			otherGame()
		}
	}

	private fun activeGame() {
		echo("=== Game ${instance.id().value} ===")
		echo("Phase: ${instance.currentPhase()}")
		//players
		echo(">Players: ")
		instance.players().forEachIndexed { index, player ->
			echo("	Player $index: ${player.id().value}")
		}
		//market
		echo(">Market: ")
		val piles = instance.supply()
		piles.forEachIndexed { index, pile ->
			val s = "---${pile.name()} : ${pile.count()}---"
			if(index != 0
				&& index % 3 == 0)
			{
				echo(s)
				echo("	", false)
			} else {
				echo("$s ; ", false)
			}
		}
		echo()
		echo(">Current Player: ${instance.currentPlayerId().value}")
		echo("  Remaining actions: ${instance.actionsRemaining()}")
		echo("  Remaining buys: ${instance.buysRemaining()}")
		echo("  Money: ${instance.coinsAvailable()}")

	}

	private fun otherGame() {
		echo("=== Game ${instance.id()} ===")
		echo(" -- Game concluded! --")
		echo("Winner: ${instance.winner().value}")
	}
}
