package hwr.oop.examples.template.core

typealias EffectStep =
            (GameContext, List<AnsweredChoice>) -> CardEffect

data class CardEffect(
    val card: Card?,
    private val context: GameContext,
    private val steps: List<EffectStep> = emptyList(),
    private val stepIndex: Int = 0,
    val pending: List<PendingChoice> = emptyList(),
    val answers: List<AnsweredChoice> = emptyList()
) {

    fun execute(): Game {

        // waiting for player input
        if (pending.isNotEmpty()) {
            return context.flush(this)
        }

        // finished all steps
        if (stepIndex >= steps.size) {
            return context.flush()
        }

        val followingEffect = steps[stepIndex]
        val next = followingEffect(context, answers)

        return next.copy(
            steps = steps,
            stepIndex = stepIndex + 1).execute()
    }



    fun answer(
        context: GameContext,
        answer: AnsweredChoice
    ): Game {

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
            card: Card?,
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
            card: Card?,
            context: GameContext,
            pending: List<PendingChoice>
        ): CardEffect {
            return CardEffect(
                card = card,
                context = context,
                pending = pending
            )
        }

        fun done(card: Card?, context: GameContext): CardEffect {
            return CardEffect(card, context)
        }
    }
}