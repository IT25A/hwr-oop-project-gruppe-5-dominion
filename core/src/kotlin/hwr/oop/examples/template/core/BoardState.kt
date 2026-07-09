package hwr.oop.examples.dominion

import hwr.oop.examples.dominion.GamePhases.DominionPurchasePhase

data class BoardState(val market: GameMarket, val players: List<Player>){

    fun piles() = market.piles

    fun nextState(activePlayer: ActivePlayer): BoardState{
        val player = activePlayer.endTurn()
        return BoardState(market, players.drop(1) + player.draw(5))
    }

    fun nextPlayer(): Player {
        return players[0]
    }

    fun purchase(activePlayer: ActivePlayer, card: Card): GamePhase {
        val result = market.purchase(activePlayer, card)
        val state = BoardState(result.market, players)
        return DominionPurchasePhase(state, result.player)
    }
}
