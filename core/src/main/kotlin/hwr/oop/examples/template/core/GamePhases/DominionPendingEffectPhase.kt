package hwr.oop.examples.template.core.GamePhases

import hwr.oop.examples.template.core.ActivePlayer
import hwr.oop.examples.template.core.AnsweredChoice
import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.CardEffect
import hwr.oop.examples.template.core.GameContext
import hwr.oop.examples.template.core.GamePendingChoice
import hwr.oop.examples.template.core.GamePhase

class DominionPendingEffectPhase(
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