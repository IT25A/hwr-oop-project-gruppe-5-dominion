package hwr.oop.examples.template.core

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

    fun flush(): Game {
        return Game.InActionPhase(state, ActivePlayer(activePlayer, activePlayerStats))
    }

    fun flush(effect: CardEffect): Game {
        return Game.EffectActive(state, ActivePlayer(activePlayer, activePlayerStats), effect)
    }
}
