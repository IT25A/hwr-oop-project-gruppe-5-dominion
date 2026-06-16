package hwr.oop.examples.template.core

sealed interface Game {
    val state: BoardState
    val activePlayer: ActivePlayer
    val activeEffect: CardEffect?

    fun piles(): Set<Pile> = state.piles()

    fun players(): List<Player> = state.players + activePlayer.player
    fun nextPlayer(): Game = throw IllegalStateException("game has concluded")
    fun updateState(): Game = throw IllegalStateException("game has concluded")
    fun play(card: Card): Game = throw GameStatusException(this.toString(), "ActionPhase")
    fun purchase(card: Card): Game = throw GameStatusException(this.toString(), "BuyPhase")
    fun effect(): CardEffect = throw GameStatusException(this.toString(), "ActiveEffect")
    fun pending(): List<GamePendingChoice> = throw GameStatusException(this.toString(), "ActiveEffect")
    fun answer(answer: AnsweredChoice): Game = throw GameStatusException(this.toString(), "ActiveEffect")

    override fun toString(): String

    class InActionPhase(
        override val state: BoardState,
        override val activePlayer: ActivePlayer,
        override val activeEffect: CardEffect? = null
    ) : Game {

        override fun toString(): String = "ActionPhase"

        override fun nextPlayer(): Game {
            return InActionPhase(
                state.nextState(activePlayer),
                ActivePlayer.create(state.nextPlayer())
            )
        }

        override fun updateState(): Game {
            return if (activePlayer.actions() > 0) {
                this
            } else {
                InPurchasePhase(state, activePlayer)
            }
        }

        override fun play(card: Card): Game {
            return activePlayer.play(card, state).updateState()
        }
    }

    class InPurchasePhase(
        override val state: BoardState,
        override val activePlayer: ActivePlayer,
        override val activeEffect: CardEffect? = null
    ) : Game {

        override fun toString(): String = "BuyPhase"

        override fun nextPlayer(): Game {
            return InActionPhase(
                state.nextState(activePlayer),
                ActivePlayer.create(state.nextPlayer())
            )
        }

        override fun updateState(): Game {
            return if (activePlayer.buys() > 0) {
                this
            } else {
                nextPlayer()
            }
        }

        override fun purchase(card: Card): Game {
            return state.purchase(activePlayer, card).updateState()
        }
    }

    class EffectActive(
        override val state: BoardState,
        override val activePlayer: ActivePlayer,
        override val activeEffect: CardEffect
    ) : Game {

        override fun toString(): String = "ActiveEffect"

        override fun nextPlayer(): Game {
            throw IllegalStateException("Cannot switch player during effect resolution")
        }

        override fun updateState(): Game = this

        override fun answer(answer: AnsweredChoice): Game {
            return activeEffect.answer(GameContext(activePlayer, state), answer)
        }

        override fun pending(): List<GamePendingChoice> {
            return activeEffect.pending
        }

        override fun effect() = activeEffect
    }

    class Finished(
        override val state: BoardState,
        override val activePlayer: ActivePlayer,
        override val activeEffect: CardEffect? = null
    ) : Game {
        override fun toString(): String = throw IllegalStateException("game has concluded")
    }

}