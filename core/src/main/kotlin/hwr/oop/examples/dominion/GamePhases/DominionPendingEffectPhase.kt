package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.AnsweredChoice
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.CardEffect
import hwr.oop.examples.dominion.GameContext
import hwr.oop.examples.dominion.GamePendingChoice
import hwr.oop.examples.dominion.GamePhase
import kotlinx.serialization.Serializable
import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.dominion.UnresolvedChoiceException

@Serializable
data class DominionPendingEffectPhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer,
    override val activeEffect: CardEffect
) : GamePhase.PendingEffectPhase, GamePhase.ActiveGamePhase() {
    override fun toString(): String = "PendingEffectPhase"

    override fun nextPhase(): GamePhase {
        throw UnresolvedChoiceException()
    }

    override fun hasActiveChoice(player: PlayerId) = activeEffect.hasActiveChoice(player)

    override fun restoreEffect(): GamePhase {
        return DominionPendingEffectPhase(state, activePlayer, activeEffect.restoreSteps())
    }

    override fun effect() = activeEffect

    override fun pending() = activeEffect.pending

    override fun firstChoiceFor(playerId: PlayerId): GamePendingChoice {
        return activeEffect.pending.filter { it.playerId == playerId }.first()
    }

    override fun answer(answer: AnsweredChoice): GamePhase {
        val destination = firstChoiceFor(answer.playerId)
        destination.assertValid(answer)
        return activeEffect.answer(GameContext(activePlayer.player, activePlayer.stats, state), answer)
    }

    override fun isActivePlayer(playerId: PlayerId): Boolean {
        return activePlayer.isSamePlayerAs(playerId)
    }
}