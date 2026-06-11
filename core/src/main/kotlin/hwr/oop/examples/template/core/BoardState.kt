package hwr.oop.examples.template.core

data class BoardState(val market: GameMarket, val players: List<Player>){

    fun piles() = market.piles

    fun nextState(activePlayer: ActivePlayer): BoardState{
        val player = activePlayer.endTurn()
        return BoardState(market, players.drop(1) + player.draw(5))
    }

    fun nextPlayer(): Player {
        return players[0]
    }

    fun purchase(activePlayer: ActivePlayer, card: Card): Game {
        val result = market.purchase(activePlayer, card)
        val state = BoardState(result.market, players)
        return Game(GameStatus.Running, state, result.player)
    }
}
