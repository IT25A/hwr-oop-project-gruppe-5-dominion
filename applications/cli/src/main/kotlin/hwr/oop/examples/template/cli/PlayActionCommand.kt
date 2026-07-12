package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import hwr.oop.examples.dominion.GameInstance

class PlayActionCommand : CliktCommand(name = "playAction") {
	private val ctx by requireObject<CliContext>()
	private val playerId by option("--player-id", help = "The ID of the player playing the action card.").required()
	private val cardName by option("--card-name", help = "The name of the action card to play from hand.").required()

	private lateinit var instance: GameInstance

	override fun run() {
		instance = ctx.loadGame()
		try{
			instance.validate(playerId)
			val result = instance.playAction(cardName)
			ctx.saveGame(result)
		} catch (ex: Exception) {
			echo("Couldn't play action:")
			echo(ex.message)
		}
	}
}
