package hwr.oop.examples.template.core

import kotlin.collections.orEmpty
import kotlin.collections.plus

class Game(
    private val state: BoardState,
    internal val activePlayer: ActivePlayer,
    private val status: GameStatus = GameStatus.Running
) {

    fun piles() = state.piles()
    fun players() = state.players + activePlayer.player

    fun isRunning() = status is GameStatus.Running

    fun next(): Game{
        requireGameRunning(status)
        return Game(GameStatus.Running, state.nextState(activePlayer), ActivePlayer.create(state.nextPlayer()))
    }

    fun play(card: Card): Game {
        requireGameRunning(status)
        return when(val result = activePlayer.play(card, state)){
            is PlayResult.Complete -> result.context.flush()
            is PlayResult.WaitingForChoice -> Game(GameStatus.WaitingForChoice(result.card, result.choices), state, activePlayer)
        }
    }

    fun purchase(card: Card): Game{
        requireGameRunning(status)
        return state.purchase(activePlayer, card)
    }

    fun answer(answer: AnsweredChoice): Game{
        require(status is GameStatus.WaitingForChoice)
        val result = status.effect.answer(answer)
        if (result.isResolved()) {
            val result = activePlayer.resume(state, result)
            return result.context.flush()
        }

        return Game(GameStatus.WaitingForChoice(result), state, activePlayer)
    }



}