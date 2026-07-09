package hwr.oop.examples.dominion

sealed interface GamePhase {
    val state: BoardState
    val activePlayer: ActivePlayer

    override fun toString(): String

    fun players() = state.players + activePlayer.player
    fun piles() = state.piles()

    interface ActionPhase : GamePhase {
        fun updateState(): GamePhase

        fun play(card: Card): GamePhase
    }

    interface PurchasePhase : GamePhase {
        fun nextPlayer(): GamePhase
        fun updateState(): GamePhase

        fun purchase(card: Card): GamePhase
    }

    interface PendingEffectPhase : GamePhase {
        fun updateState(): GamePhase = this

        val activeEffect: CardEffect

        fun effect(): CardEffect
        fun pending(): List<GamePendingChoice>
        fun answer(answer: AnsweredChoice): GamePhase
    }

    interface Finished : GamePhase

}