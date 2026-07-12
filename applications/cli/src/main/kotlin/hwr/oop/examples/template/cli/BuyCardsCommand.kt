package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.optionalValue
import com.github.ajalt.clikt.parameters.options.required
import hwr.oop.examples.dominion.GameInstance

class BuyCardsCommand : CliktCommand(name = "buyCards") {
	private val ctx by requireObject<CliContext>()
	private val playerId by option("--player-id", help = "The ID of the player buying the cards.").required()
	private val cardsToBuy by option(
		"--card-name",
		help = "Name of a card to buy from the supply. Pass multiple times to buy several cards in order. Omit entirely to forfeit remaining buys."
	).multiple()

	private lateinit var instance: GameInstance

	override fun run() {
		instance = ctx.loadGame()
		instance.validate(playerId)
		if(cardsToBuy.all { it.isBlank() }) {
			ctx.saveGame(instance.skipPhase())
			return
		}
		try {
			ctx.saveGame(instance.purchase(cardsToBuy))
		}catch (ex: Exception){
			echo("Couldn't buy card:")
			echo(ex.message)
		}
	}
}

