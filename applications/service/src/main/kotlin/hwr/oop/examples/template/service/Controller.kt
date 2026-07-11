package hwr.oop.examples.template.service

import hwr.oop.examples.dominion.AnsweredChoice
import hwr.oop.examples.dominion.DominionPersistence
import hwr.oop.examples.dominion.CardEffect
import hwr.oop.examples.dominion.EffectStep
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.GamePendingChoice
import hwr.oop.examples.dominion.Pile
import hwr.oop.examples.dominion.Player
import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.template.service.api.GameActionApi
import hwr.oop.examples.template.service.api.GameApi
import hwr.oop.examples.template.service.model.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    val persistence: DominionPersistence
) : GameApi, GameActionApi {

    override fun getGame(gameId: String?): ResponseEntity<GameState> {
        require(gameId != null) { "Game ID is null" }
        val game = persistence.load(gameId)
        return map(game)
    }

    private fun map(game: GameInstance): ResponseEntity<GameState> {
        val state = GameState(
            /* gameId = */ game.id(),
            /* status = */ game.status(),
            /* currentPlayerId = */ game.currentPlayerId(),
            /* currentPhase = */ game.currentPhase(),
            /* actionsRemaining = */ game.actionsRemaining(),
            /* buysRemaining = */ game.buysRemaining(),
            /* coinsAvailable = */ game.coinsAvailable(),
            /* supply = */ mapSupplies(game.supply()),
            /* players = */ mapPlayers(game.players()),
            /* effectStack = */ game.effect().mapEffect()
        )
        return ResponseEntity.ok(state)
    }

    private fun mapSupplies(piles: Set<Pile>): List<SupplyPile> {
        return piles.map { SupplyPile(it.name(), it.count()) }
    }

    private fun mapPlayers(players: List<Player>): List<PlayerState> {
        return players.map { player ->
            PlayerState(
                player.id().value,
                player.currentHand().map { it.toString() },
                player.playArea(),
                player.currentDiscard().map { it.toString() },
                player.deckSize()
            )
        }
    }

    private fun CardEffect.mapEffect(): List<ActiveEffect> {
        return remainingSteps()
            .mapIndexed { index, step ->
                ActiveEffect(
                    card.name,
                    instigatingPlayer().value,
                    step.explanation,
                    if (index == 0)
                        pending.map { mapChoice(it) }
                    else
                        emptyList()
                )
            }
    }

    override fun startGame(startGameRequest: @Valid StartGameRequest?): ResponseEntity<GameCreatedResponse> {
        require(startGameRequest != null) { "startGameRequest must not be null" }
        val players = startGameRequest.playerIds
        val kingdomCards = startGameRequest.kingdomCards
        val game = GameInstance.create(players.toList(), kingdomCards.toList())
        persistence.save(game)
        return ResponseEntity.ok(GameCreatedResponse(game.id()))
    }

    override fun buyCard(
        gameId: String?,
        buyCardsRequest: @Valid BuyCardsRequest?,
    ): ResponseEntity<GameState> {
        require(gameId != null) { "Game ID is required" }
        require(buyCardsRequest != null) { "buyCardsRequest is required" }

        val game = persistence.load(gameId).validate(buyCardsRequest.playerId)

        return map(game.purchase(buyCardsRequest.cardsToBuy))
    }

    fun mapChoice(choice: GamePendingChoice): PendingChoice {
        return PendingChoice(
            choice.playerId.value,
            choice.choiceType,
            choice.description,
            choice.options,
            choice.minSelections,
            choice.maxSelections
        )
    }

    override fun getChoices(gameId: String?): ResponseEntity<PendingChoicesResponse> {
        require(gameId != null) { "Game ID is required" }
        val game = persistence.load(gameId)

        val choices = game.choices()
        val pendingChoices = choices.map {
            mapChoice(it)
        }

        return ResponseEntity.ok(PendingChoicesResponse(pendingChoices))
    }

    override fun makeChoice(
        gameId: String?,
        makeChoiceRequest: @Valid MakeChoiceRequest?,
    ): ResponseEntity<GameState> {
        require(gameId != null) { "Game ID is required" }
        require(makeChoiceRequest != null) { "makeChoiceRequest must not be null" }

        val game = persistence.load(gameId).validate(makeChoiceRequest.playerId)

        val result = game.makeChoice(AnsweredChoice(PlayerId(makeChoiceRequest.playerId), makeChoiceRequest.selectedOptions))
        return map(result)
    }

    override fun playAction(
        gameId: String?,
        playActionRequest: @Valid PlayActionRequest?,
    ): ResponseEntity<GameState> {
        require(gameId != null) { "Game ID is required" }
        require(playActionRequest != null) { "playActionRequest is required" }

        val game = persistence.load(gameId).validate(playActionRequest.playerId)

        val result = game.playAction(playActionRequest.cardName)
        return map(result)
    }

    override fun playTreasures(
        gameId: String?,
        playTreasuresRequest: @Valid PlayTreasuresRequest?,
    ): ResponseEntity<GameState> {
        require(gameId != null) { "Game ID is required" }
        require(playTreasuresRequest != null) { "playTreasuresRequest is required" }

        val game = persistence.load(gameId).validate(playTreasuresRequest.playerId)

        val result = game.playTreasures(playTreasuresRequest.cardNames)
        return map(result)
    }

}
