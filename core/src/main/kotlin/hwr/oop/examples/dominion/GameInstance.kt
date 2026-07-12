package hwr.oop.examples.dominion
import hwr.oop.examples.dominion.GamePhases.DominionActionPhase
import kotlinx.serialization.Serializable

@Serializable
data class GameInstance(private val game: GamePhase, private val id: GameID) {

    fun id()= id

    fun winner(): PlayerId {
        require(game is GamePhase.Finished) { "game has not ended yet" }
        return game.winner
    }

    fun status(): String {
        if(game is GamePhase.Finished) {
            return "FINISHED"
        }

        return "IN_PROGRESS"
    }

    fun currentPlayerId(): PlayerId {
        return assertActiveGame("current player").currentPlayer()
    }

    fun currentPhase(): String {
        return game.toString()
    }

    fun actionsRemaining(): Int {
        return assertActiveGame("remaining actions").actionsRemaining()
    }

    fun buysRemaining(): Int {
        return assertActiveGame("remaining buys").buysRemaining()
    }

    fun coinsAvailable(): Int {
        return assertActiveGame("available coins").coinsAvailable()
    }

    fun supply(): Set<Pile> {
        return assertActiveGame("supply").piles()
    }

     fun players(): List<Player> {
         assertActiveGame("players")
         return (game as GamePhase.ActiveGamePhase).players()
    }

    private fun assertActiveGame(property: String): GamePhase.ActiveGamePhase {
        require(game is GamePhase.ActiveGamePhase) { "can only read $property in an ActiveGamePhase" }
        return game
    }

    fun effect(): CardEffect {
        require(game is GamePhase.PendingEffectPhase) { "no effect active" }
        return game.effect()
    }

    fun choices(): List<GamePendingChoice>{
        require(game is GamePhase.PendingEffectPhase) { "no effect active thus no choices exist" }
        return game.pending()
    }

    fun playAction(cardName: String): GameInstance{
        if(game !is GamePhase.ActionPhase ) {
            throw IllegalStateException("Cannot play action while not in ActionPhase")
        }

        return GameInstance(game.play(Card.byName(cardName)), id)
    }

    fun playTreasures(cardNames: List<String>): GameInstance {
        if(game !is GamePhase.ActionPhase ) {
            throw IllegalStateException("Cannot play treasures while not in ActionPhase")
        }

        val cards = Card.byNames(cardNames)
        val updated = cards.fold(game) { current, card ->
                if(!card.isTreasure()){
                    throw NoTreasureException(card)
                }
                current.play(card) as GamePhase.ActionPhase
        }
        return GameInstance(updated, id)
    }

    fun purchase(cardNames: List<String>): GameInstance{
        if(game !is GamePhase.PurchasePhase) {
            throw IllegalStateException("Cannot buy cards while not in PurchasePhase")
        }

        val cards = Card.byNames(cardNames)
        val updated = cards.fold(game as GamePhase) { current, card ->
            when (current) {
                is GamePhase.PurchasePhase -> {
                    current.purchase(card)
                }

                is GamePhase.ActionPhase -> {
                    current.updateState()
                }

                else -> {
                    current
                }
            }
        }

        return GameInstance(updated, id)
    }

    fun makeChoice(answer: AnsweredChoice): GameInstance{
        if(game !is GamePhase.PendingEffectPhase ) {
            throw IllegalStateException("Cannot make choice while not in PendingEffectPhase")
        }

        return GameInstance(game.answer(answer), id)
    }

    fun skipPhase(): GameInstance {
        return GameInstance(game.nextPhase(), id)
    }

    fun validate(requestingPlayer: PlayerId? = null): GameInstance {
        if(game is GamePhase.ActiveGamePhase) {
            if(game is GamePhase.PendingEffectPhase) {
                require(requestingPlayer != null) { "choice must have a player assigned" }
                require(game.hasActiveChoice(requestingPlayer)) { "this player has no active choice" }
            } else {
                require(requestingPlayer != null) { "cannot run active game phase when requestingPlayer is null" }
                require(game.isActivePlayer(requestingPlayer)) { "player ${requestingPlayer.value} is not the active player" }
            }
        }

        return this
    }

    fun validate(requestingPlayer: String): GameInstance {
        return validate(PlayerId(requestingPlayer))
    }

    fun currentPlayersHand(): List<Card> {
        require(game is GamePhase.ActiveGamePhase) { "players hand only accessible during active game" }
        return game.currentPlayersHand()
    }

    fun restoreEffect(): GameInstance {
        return if(game is GamePhase.PendingEffectPhase) {
            GameInstance(game.restoreEffect(), id)
        } else {
            this
        }
    }

    fun getChoiceFor(playerId: PlayerId): GamePendingChoice {
        require(game is GamePhase.PendingEffectPhase) { "no effect active thus no choices exist" }
        return game.firstChoiceFor(playerId)
    }

    companion object{
        fun create(
            playerIds: List<String>,
            kingdomCards: List<String>
        ): GameInstance {
            val players = playerIds.drop(1).map { Player(PlayerId(it)) }
            val startingPlayer = Player(PlayerId(playerIds[0]), wasFirst = true)
            val market = createMarket(kingdomCards)
            val state = BoardState(market, players)
            val game = DominionActionPhase(state, ActivePlayer.create(startingPlayer.draw(5)))
            return GameInstance(game, GameID.random())
        }

        private fun createMarket(kingdomCards: List<String>): GameMarket {
            return GameMarket(Card.byNames(kingdomCards).map{ Pile(it, 10)}.toSet())
        }
    }

}
