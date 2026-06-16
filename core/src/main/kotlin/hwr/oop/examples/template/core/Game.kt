package hwr.oop.examples.template.core

import kotlin.collections.plus

class Game(
    private val state: BoardState,
    internal val activePlayer: ActivePlayer,
    private val phase: GamePhase,
    private val activeEffect: CardEffect? = null
) {

    fun piles() = state.piles()
    fun players() = state.players + activePlayer.player

    fun nextPlayer(): Game{
        return Game(state.nextState(activePlayer), ActivePlayer.create(state.nextPlayer()), GamePhase.ActionPhase)
    }

    fun updateState(): Game{
        return when(phase){
            GamePhase.ActionPhase -> {
                if(activePlayer.actions() > 0){
                    this
                } else {
                    Game(state, activePlayer, GamePhase.BuyPhase)
                }
            }

            GamePhase.BuyPhase -> {
                if(activePlayer.buys() > 0){
                    this
                } else {
                    nextPlayer()
                }
            }

            else -> this
        }
    }

    fun play(card: Card): Game {
        if(phase !is GamePhase.ActionPhase){
            throw GameStatusException(phase, GamePhase.ActionPhase)
        }
        return activePlayer.play(card, state)
    }

    fun purchase(card: Card): Game{
        if(phase !is GamePhase.BuyPhase){
            throw GameStatusException(phase, GamePhase.BuyPhase)
        }
        return state.purchase(activePlayer, card)
    }

    fun answer(answer: AnsweredChoice): Game {
        if (phase !is GamePhase.EffectActive) {
            throw GameStatusException(phase, GamePhase.EffectActive)
        }

        val effect = requireNotNull(activeEffect)
        return effect.answer(GameContext(activePlayer, state),  answer)
    }


}