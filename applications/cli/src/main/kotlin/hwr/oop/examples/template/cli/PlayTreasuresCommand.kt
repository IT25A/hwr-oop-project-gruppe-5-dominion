package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.Pile

class PlayTreasuresCommand : CliktCommand(name = "playTreasures") {
	private val ctx by requireObject<CliContext>()
	private val playerId by option("--player-id", help = "The ID of the player playing the treasure cards.").required()
	private val cardNames by option(
		"--card-name",
		help = "Name of a treasure card to play from hand. Pass multiple times to play several cards in order."
	).multiple(required = true)

	private lateinit var instance: GameInstance

	override fun run() {
		instance = ctx.loadGame()
		try {
			instance.validate(playerId)
			instance.playTreasures(cardNames)
		} catch (ex: Exception) {
			echo("couldn't play all treasures")
			echo(ex.message)
		}
	}
}
