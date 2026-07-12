package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import hwr.oop.examples.dominion.AnsweredChoice
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.PlayerId
import java.awt.Choice

class MakeChoiceCommand : CliktCommand(name = "makeChoice") {
	private val ctx by requireObject<CliContext>()
	private val playerId by option("--player-id", help = "The ID of the player submitting the choice.").required()
	private val selectedOptions by option(
		"--selected-option",
		help = "An option to select (option label). Pass multiple times for multi-select choices. Omit entirely to select nothing."
	).int().multiple()

	private lateinit var instance: GameInstance

	override fun run(){
		instance = ctx.loadGame()
		try {
			instance.validate(playerId)
			val choice = instance.getChoiceFor(PlayerId(playerId))
			val opts = selectedOptions.map{ choice.options[it] }
			ctx.saveGame(
				instance.makeChoice(AnsweredChoice(PlayerId(playerId), opts))
			)
		}catch (ex: Exception){
			echo("couldn't make choice")
			echo(ex.message)
		}
	}
}

