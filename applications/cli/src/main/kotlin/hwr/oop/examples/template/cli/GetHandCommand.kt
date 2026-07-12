package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import hwr.oop.examples.dominion.GameInstance

class GetHandCommand : CliktCommand(name = "getHand") {
    private val ctx by requireObject<CliContext>()

    private lateinit var instance: GameInstance

    override fun run() {
        instance = ctx.loadGame()
        val hand = instance.currentPlayersHand()
        echo("Hand", false)
        hand.forEachIndexed { index, card ->
            if (index != 0 &&
                index % 5 == 0) {
                echo(card.name)
            } else {
                echo(" - ${card.name}", false)
            }
        }
    }
}