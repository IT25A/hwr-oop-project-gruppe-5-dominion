package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.PlayerId
import kotlinx.serialization.Serializable

@Serializable
data class DominionPurchasePhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer
) : GamePhase.PurchasePhase, GamePhase.ActiveGamePhase() {
    override fun toString() = "PurchasePhase"

    override fun nextPhase(): GamePhase {
        val next = state.nextPlayer()
        if(state.gameEndingRequirementMet() && next.wasFirst){
            val pointsPerPlayer = players().map { it.id() to it.calculatePoints(state) }.sortedByDescending { it.second }
            return DominionFinishedGame(pointsPerPlayer[0].first)
        }

        return DominionActionPhase(
            state.nextState(activePlayer),
            ActivePlayer.create(next)
        )
    }

    override fun updateState(): GamePhase {
        return if(activePlayer.buys() > 0){
            this
        } else {
            nextPhase()
        }
    }

    override fun purchase(card: Card): GamePhase {
        return (state.purchase(activePlayer, card) as GamePhase.PurchasePhase).updateState()
    }

    override fun isActivePlayer(playerId: PlayerId): Boolean {
        return activePlayer.isSamePlayerAs(playerId)
    }

}