package hwr.oop.examples.template.core

import kotlin.collections.plus

class Game(
    private val state: BoardState,
    internal val activePlayer: ActivePlayer,
    private val status: GameStatus
) {

    fun piles() = state.piles()
    fun players() = state.players + activePlayer.player

    fun next(): Game{
        return Game(state.nextState(activePlayer), ActivePlayer.create(state.nextPlayer()), GameStatus.ActionPhase)
    }

    fun updateState(): Game{
        return when(status){
            GameStatus.ActionPhase -> {
                if(activePlayer.actions() > 0){
                    this
                } else {
                    Game(state, activePlayer, GameStatus.BuyPhase)
                }
            }

            GameStatus.BuyPhase -> {
                if(activePlayer.buys() > 0){
                    this
                } else {
                    next()
                }
            }

            else -> this
        }
    }

    fun play(card: Card): Game {
        return when(val result = activePlayer.play(card, state)){
            is PlayResult.Complete -> result.context.flush()
            is PlayResult.WaitingForChoice -> Game(state, activePlayer, GameStatus.EffectActive(result.effect))
        }
    }

    fun purchase(card: Card): Game{
        return state.purchase(activePlayer, card)
    }

    fun answer(answer: AnsweredChoice): Game{
        require(status is GameStatus.EffectActive)
        val result = status.effect.answer(answer)
        if (result.isResolved()) {
            val result = activePlayer.resume(state, result)
            return result.context.flush()
        }

        return Game(state, activePlayer, GameStatus.EffectActive(result))
    }



}