package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CardEffect(
    val card: Card,
    private val context: GameContext ,
    @Transient
    internal val steps: List<EffectStep> = emptyList(),

    private val stepIndex: Int = 0,
    val pending: List<GamePendingChoice> = emptyList(),
    val answers: List<AnsweredChoice> = emptyList()
) {
    fun remainingSteps(): List<EffectStep> = steps.drop(stepIndex)
    fun instigatingPlayer() = context.currentPlayerId()

    fun restoreSteps(): CardEffect {
        val originalEffect = card.effect(context)!!

        return copy(
            steps = originalEffect.steps
        )
    }

    fun hasActiveChoice(player: PlayerId) : Boolean {
        return pending.any { it.playerId == player }
    }

    fun execute(): GamePhase {
        if (pending.isNotEmpty()) {
            return context.flush(this)
        }

        if (stepIndex >= steps.size) {
            return context.flush()
        }

        val followingEffect = steps[stepIndex]
        val next = followingEffect.execute(context, answers)

        return next.copy(
            steps = steps,
            stepIndex = stepIndex + 1).execute()
    }

    fun answer(
        context: GameContext,
        answer: AnsweredChoice
    ): GamePhase {

        val updated = CardEffect(
            card = card,
            context = context,
            steps = steps,
            stepIndex = stepIndex,
            pending = emptyList(),
            answers = answers + answer
        )

        return updated.execute()
    }

    companion object {

        fun stepwise(
            card: Card,
            context: GameContext,
            steps: List<EffectStep>
        ): CardEffect {
            return CardEffect(
                card = card,
                context = context,
                steps = steps
            )
        }

        fun pending(
            card: Card,
            context: GameContext,
            pending: List<GamePendingChoice>
        ): CardEffect {
            return CardEffect(
                card = card,
                context = context,
                pending = pending,
            )
        }

        fun done(card: Card, context: GameContext): CardEffect {
            return CardEffect(card, context)
        }
    }
}