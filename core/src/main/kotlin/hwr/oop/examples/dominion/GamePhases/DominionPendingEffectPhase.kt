package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.AnsweredChoice
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.CardEffect
import hwr.oop.examples.dominion.GameContext
import hwr.oop.examples.dominion.GamePendingChoice
import hwr.oop.examples.dominion.GamePhase
import kotlinx.serialization.Serializable

@Serializable
data class DominionPendingEffectPhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer,
    override val activeEffect: CardEffect
) : GamePhase.PendingEffectPhase {
    override fun toString(): String = "PendingEffectPhase"

    override fun effect() = activeEffect

    override fun pending() = activeEffect.pending

    override fun answer(answer: AnsweredChoice): GamePhase {
        return activeEffect.answer(GameContext(activePlayer.player, activePlayer.stats, state), answer)
    }
}