package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
sealed interface GamePhase {

    override fun toString(): String

    fun nextPhase(): GamePhase

    interface ActionPhase : GamePhase {
        fun updateState(): GamePhase
        fun play(card: Card): GamePhase
    }

    interface PurchasePhase : GamePhase {
        fun updateState(): GamePhase
        fun purchase(card: Card): GamePhase
    }

    interface PendingEffectPhase : GamePhase {
        val activeEffect: CardEffect

        fun hasActiveChoice(player: PlayerId): Boolean

        fun restoreEffect(): GamePhase

        fun effect(): CardEffect
        fun pending(): List<GamePendingChoice>
        fun firstChoiceFor(playerId: PlayerId): GamePendingChoice
        fun answer(answer: AnsweredChoice): GamePhase
    }

    abstract class ActiveGamePhase : GamePhase {
        abstract val state: BoardState
        abstract val activePlayer: ActivePlayer

        abstract fun isActivePlayer(playerId: PlayerId): Boolean

        fun players() = state.players + activePlayer.player
        fun piles() = state.piles()
        fun currentPlayersHand() = activePlayer.hand()

        fun actionsRemaining() = activePlayer.actions()
        fun coinsAvailable() = activePlayer.coins()
        fun buysRemaining() = activePlayer.buys()
        fun currentPlayer() = activePlayer.id()

        fun currentPlayersPoints() = activePlayer.points(state)
    }

    interface Finished : GamePhase{
        val winner: PlayerId
    }

}