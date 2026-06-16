package hwr.oop.examples.template.core

class GameContext(
    private val activePlayer: ActivePlayer,
    private val state: BoardState
) {
    fun player() = activePlayer
    fun state() = state

    fun discard(cards: List<Card>): GameContext {
        return GameContext(activePlayer.discard(cards), state)
    }

    fun flush(): Game {
        return Game.InActionPhase(state, activePlayer)
    }

    fun flush(effect: CardEffect): Game {
        return Game.EffectActive(state, activePlayer, effect)
    }
}
