package hwr.oop.examples.template.core

class CardEffectBuilder(
    private val card: Card
) {

    private val steps = mutableListOf<EffectStep>()

    fun step(
        description: String,
        execute: (GameContext, List<AnsweredChoice>) -> CardEffect
    ) = apply {
        steps.add(
            EffectStep(
                description,
                execute
            )
        )
    }

    fun build(context: GameContext): CardEffect {
        return CardEffect.stepwise(card, context, steps)
    }
}