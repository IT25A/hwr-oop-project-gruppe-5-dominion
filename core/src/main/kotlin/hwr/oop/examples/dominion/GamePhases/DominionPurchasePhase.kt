package hwr.oop.examples.dominion.GamePhases

import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.PlayerId

class DominionPurchasePhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer
) : GamePhase.PurchasePhase, GamePhase.ActiveGamePhase {
    override fun toString() = "PurchasePhase"

    override fun nextPlayer(): GamePhase {
        return DominionActionPhase(
            state.nextState(activePlayer),
            ActivePlayer.create(state.nextPlayer())
        )
    }

    override fun updateState(): GamePhase {
        return if(activePlayer.buys() > 0){
            this
        } else {
            nextPlayer()
        }
    }

    override fun purchase(card: Card): GamePhase {
        return (state.purchase(activePlayer, card) as GamePhase.PurchasePhase).updateState()
    }

    override fun isActivePlayer(playerId: PlayerId): Boolean {
        return activePlayer.isSamePlayerAs(playerId)
    }

}