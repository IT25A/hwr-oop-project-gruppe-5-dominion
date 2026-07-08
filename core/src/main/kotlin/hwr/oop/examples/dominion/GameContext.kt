package hwr.oop.examples.dominion

import hwr.oop.examples.dominion.GamePhases.DominionActionPhase
import hwr.oop.examples.dominion.GamePhases.DominionPendingEffectPhase

class GameContext(
    private val activePlayer: Player,
    private val activePlayerStats: Stats,
    private val state: BoardState
) {

    fun currentPlayerId() = activePlayer.id()

    fun playerHandSize() = activePlayer.cards.handSize()
    fun currentHand() = activePlayer.currentHand()

    fun draw(count: Int): GameContext {
        return GameContext(activePlayer.draw(count), activePlayerStats, state)
    }

    fun discard(cards: List<Card>): GameContext {
        return GameContext(activePlayer.discard(cards),activePlayerStats , state)
    }

    fun flush(): GamePhase {
        return DominionActionPhase(state, ActivePlayer(activePlayer, activePlayerStats))
    }

    fun flush(effect: CardEffect): GamePhase {
        return DominionPendingEffectPhase(state, ActivePlayer(activePlayer, activePlayerStats), effect)
    }
}
