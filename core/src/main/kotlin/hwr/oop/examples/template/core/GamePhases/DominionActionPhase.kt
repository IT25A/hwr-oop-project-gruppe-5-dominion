package hwr.oop.examples.template.core.GamePhases

import hwr.oop.examples.template.core.ActivePlayer
import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.GamePhase

class DominionActionPhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer
) : GamePhase.ActionPhase {
    override fun toString(): String = "ActionPhase"

    override fun updateState(): GamePhase {
        return if (activePlayer.actions() > 0){
            this
        } else {
            DominionPurchasePhase(state, activePlayer);
        }
    }

    override fun play(card: Card): GamePhase {
        return (activePlayer.play(card, state) as GamePhase.ActionPhase).updateState()
    }
}