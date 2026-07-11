package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GamePhase
import kotlinx.serialization.Serializable

@Serializable
data class DominionActionPhase(
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