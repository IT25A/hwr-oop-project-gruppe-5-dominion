package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.option
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.GamePendingChoice
import hwr.oop.examples.dominion.GamePhase

class GetChoicesCommand : CliktCommand(name = "getChoices") {
	private val ctx by requireObject<CliContext>()

	private lateinit var instance: GameInstance

	override fun run() {
		instance = ctx.loadGame()
		try {
			val choices = instance.choices().sortedBy { it.playerId.value }
			choices.forEachIndexed { index, choice ->
				echo("=== Choice ${index + 1} ===")
				show(choice)
			}
		} catch (ex: Exception) {
			echo(ex.message)
		}
	}

	private fun show(choice: GamePendingChoice) {
		echo("Player ID      : ${choice.playerId.value}")
		echo("Type           : ${choice.choiceType}")
		echo("Description    : ${choice.description}")
		echo("Min selections : ${choice.minSelections}")
		echo("Max selections : ${choice.maxSelections}")

		echo("Options:")
		choice.options.forEachIndexed { index, option ->
			echo("  ${index + 1}.) $option")
		}

		echo("")
	}
}
