package hwr.oop.examples.template.core

class GameContext(
    private val activePlayer: ActivePlayer,
    private val state: BoardState
) {

    fun player() = activePlayer
    fun state() = state
    fun flush(): Game {
        return Game(state, activePlayer)
    }
}
