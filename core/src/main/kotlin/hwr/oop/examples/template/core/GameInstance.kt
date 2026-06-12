package hwr.oop.examples.template.core

import java.util.UUID

class GameInstance(private val game: Game, private val id: String) {

    fun id() = id

    fun status(): String {
        TODO("Not yet implemented")
    }

    fun currentPlayerId(): String {
        return game.activePlayer.id()
    }

    fun currentPhase(): String {
        TODO("Not yet implemented")
    }

    fun actionsRemaining(): Int {
        return game.activePlayer.actions()
    }

    fun buysRemaining(): Int {
        return game.activePlayer.buys()
    }

    fun coinsAvailable(): Int {
        return game.activePlayer.coins()
    }

    fun supply(): Set<Pile> {
        return game.piles()
    }

     fun players(): List<Player> {
        return game.players()
    }

    fun effects() {
        TODO("Not yet implemented")
    }

    fun isActivePlayer(playerId: String): Boolean {
        return game.activePlayer.id() == playerId
    }

    fun playAction(cardName: String): GameInstance{
        return GameInstance(game.play(Card.byName(cardName)), id)
    }

    fun playTreasures(cardNames: List<String>): GameInstance {
        val cards = cardNames.map { Card.byName(it) }
        val updated = cards.fold(game) { current, card ->
                requireTreasure(card)
                val result = current.play(card)
                if(!result.isRunning())
                    throw NoTreasureException(card)
                else result
        }
        return GameInstance(updated, id)
    }

    fun purchase(): GameInstance{
        return this
    }

    fun makeChoice(answer: AnsweredChoice): GameInstance{
        return GameInstance(game.answer(answer), id)
    }

    companion object{
        fun create(
            players: List<String>,
            kingdomCards: List<String>
        ): GameInstance {
            val players = players.map { Player(it, PlayerCards()) }
            val market = createMarket(kingdomCards)
            val state = BoardState(market, players.drop(1))
            val game = Game(GameStatus.Running, state, ActivePlayer.create(players[0]))
            val gId = UUID.randomUUID().toString()
            return GameInstance(game, gId)
        }

        private fun createMarket(kingdomCards: List<String>): GameMarket {
            return GameMarket(emptySet())
        }
    }

}
