package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.GamePendingChoice
import hwr.oop.examples.dominion.GamePhase
import kotlinx.serialization.Serializable
import hwr.oop.examples.dominion.PlayerId

@Serializable
data class DominionActionPhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer
) : GamePhase.ActionPhase, GamePhase.ActiveGamePhase() {
    override fun toString(): String = "ActionPhase"

    override fun nextPhase(): GamePhase {
        return DominionPurchasePhase(state, activePlayer)
    }

    override fun updateState(): GamePhase {
        return if (activePlayer.actions() > 0){
            this
        } else {
            DominionPurchasePhase(state, activePlayer);
        }
    }

    override fun play(card: Card): GamePhase {
        return when (val result = activePlayer.play(card, state)) {
            is GamePhase.ActionPhase -> result.updateState()
            else -> result
        }
    }

    override fun isActivePlayer(playerId: PlayerId): Boolean {
        return activePlayer.isSamePlayerAs(playerId)
    }
}