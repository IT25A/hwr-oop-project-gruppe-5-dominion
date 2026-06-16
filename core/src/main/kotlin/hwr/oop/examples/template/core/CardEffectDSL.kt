package hwr.oop.examples.template.core

object CardEffectDSL {
    fun effect(
        card: Card,
        context: GameContext,
        block: CardEffectBuilder.() -> Unit
    ): CardEffect {
        return CardEffectBuilder(card)
            .apply(block)
            .build(context)
    }
}