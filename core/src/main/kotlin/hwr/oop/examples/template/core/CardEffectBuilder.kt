package hwr.oop.examples.template.core

class CardEffectBuilder(
    private val card: Card?
) {

    private val steps = mutableListOf<EffectStep>()

    fun step(block: EffectStep) = apply {
        steps.add(block)
    }

    fun build(context: GameContext): CardEffect {
        return CardEffect.stepwise(card, context, steps)
    }
}