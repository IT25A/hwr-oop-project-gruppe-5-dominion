package hwr.oop.examples.template.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import hwr.oop.examples.dominion.GameInstance

class SkipPhaseCommand : CliktCommand(name = "skipPhase"){
    private val ctx by requireObject<CliContext>()

    private lateinit var instance: GameInstance

    override fun run() {
        instance = ctx.loadGame()
        ctx.saveGame(instance.skipPhase())
    }

}
