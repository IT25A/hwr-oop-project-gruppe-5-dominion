package hwr.oop.examples.dominion
import hwr.oop.examples.dominion.GamePhases.DominionActionPhase
import java.util.UUID

class GameInstance(private val game: GamePhase, private val id: GameID) {

    fun id()= id

    fun status(): String {
        if(game is GamePhase.Finished) {
            return "FINISHED"
        }

        return "IN_PROGRESS"
    }

    fun currentPlayerId(): String {
        return game.activePlayer.id().value
    }

    fun currentPhase(): String {
        return game.toString()
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

    fun effect(): CardEffect {
        if(game !is GamePhase.PendingEffectPhase  ) {
            throw IllegalStateException("Game is not in PendingEffectPhase")
        }

        return game.effect()
    }

    fun choices(): List<GamePendingChoice>{
        if(game !is GamePhase.PendingEffectPhase){
            throw IllegalStateException("No choices exist while not in PendingEffectPhase")
        }

        return game.pending()
    }

    fun isActivePlayer(playerId: String): Boolean {
        return game.activePlayer.id().value == playerId
    }

    fun playAction(cardName: String): GameInstance{
        if(game !is GamePhase.ActionPhase ) {
            throw IllegalStateException("Cannot play action while not in ActionPhase")
        }

        return GameInstance(game.play(Card.byName(cardName)), id)
    }

    fun playTreasures(cardNames: List<String>): GameInstance {
        if(game !is GamePhase.ActionPhase ) {
            throw IllegalStateException("Cannot play action while not in ActionPhase")
        }

        val cards = cardNames.map { Card.byName(it) }
        val updated = cards.fold(game) { current, card ->
                if(!card.isTreasure()){
                    throw NoTreasureException(card)
                }
                current.play(card) as GamePhase.ActionPhase
        }
        return GameInstance(updated, id)
    }

    fun purchase(cards: List<String>): GameInstance{
        return this
    }

    fun makeChoice(answer: AnsweredChoice): GameInstance{
        if(game !is GamePhase.PendingEffectPhase ) {
            throw IllegalStateException("Cannot make choice while not in PendingEffectPhase")
        }

        return GameInstance(game.answer(answer), id)
    }

    companion object{
        fun create(
            players: List<String>,
            kingdomCards: List<String>
        ): GameInstance {
            val players = players.map { Player(PlayerId(it), PlayerCards()) }
            val market = createMarket(kingdomCards)
            val state = BoardState(market, players.drop(1))
            val game = DominionActionPhase(state, ActivePlayer.create(players[0]))
            return GameInstance(game, GameID.random())
        }

        private fun createMarket(kingdomCards: List<String>): GameMarket {
            return GameMarket(kingdomCards.map { Pile(Card.byName(it), 10) }.toSet())
        }
    }

}
