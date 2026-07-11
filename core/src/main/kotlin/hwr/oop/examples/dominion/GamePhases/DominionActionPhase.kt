package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.PlayerId

class DominionActionPhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer
) : GamePhase.ActionPhase, GamePhase.ActiveGamePhase {
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

    override fun isActivePlayer(playerId: PlayerId): Boolean {
        return activePlayer.isSamePlayerAs(playerId)
    }
}