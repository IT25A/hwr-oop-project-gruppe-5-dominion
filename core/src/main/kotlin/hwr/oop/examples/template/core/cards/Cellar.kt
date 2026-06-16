package hwr.oop.examples.template.core.cards

import hwr.oop.examples.template.core.Card
import hwr.oop.examples.template.core.CardDefinition
import hwr.oop.examples.template.core.CardEffect
import hwr.oop.examples.template.core.CardType
import hwr.oop.examples.template.core.PendingChoiceBuilder
import hwr.oop.examples.template.core.CardEffectDSL
import hwr.oop.examples.template.core.GameContext

class Cellar: CardDefinition {
    override val types: List<CardType> = listOf(CardType.ACTION)
    override val name: String = "Cellar"
    override val cost: Int = 5
    override val draw: Int = 0
    override val actions: Int = 1
    override val buys: Int = 0
    override val gold: Int = 0
    override val points: Int = 0

    override fun getEffect(initial: GameContext): CardEffect {

        return CardEffectDSL.effect(Card.CELLAR, initial) {

            step("Player needs to enter how many cards to discard.") { context, _ ->
                CardEffect.pending(
                    Card.CELLAR,
                    context,
                    pending = listOf(
                        PendingChoiceBuilder()
                            .playerId(context.player().id())
                            .type("SELECT_NUMBER")
                            .description("How many cards to discard?")
                            .options((0..5).map { it.toString() })
                            .min(1)
                            .max(1)
                            .build()
                    )
                )
            }

            step("Player needs to choose which cards to discard") { context, answers ->

                val count = answers.first().selectedOptions.first().toInt()

                CardEffect.pending(
                    Card.CELLAR, context,
                    pending = listOf(
                            PendingChoiceBuilder()
                                .playerId(context.player().id())
                                .type("SELECT_CARDS")
                                .description("Choose cards to discard")
                                .options(context.player().player.currentHand().map { it.name })
                                .min(count)
                                .max(count)
                                .build()
                    )
                )
            }

            step("discard chosen cards and draw as many new cards") { context, answers ->
                val selected = answers.first().selectedOptions
                val cards = selected.map { Card.byName(it) }
                val result = context.discard(cards)
                CardEffect.done(Card.CELLAR, result)
            }
        }
    }
}