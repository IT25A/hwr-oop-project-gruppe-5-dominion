package hwr.oop.examples.template.core.GamePhases

import hwr.oop.examples.template.core.ActivePlayer
import hwr.oop.examples.template.core.BoardState
import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.GamePhase

class DominionPurchasePhase(
    override val state: BoardState,
    override val activePlayer: ActivePlayer
) : GamePhase.PurchasePhase {
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

}