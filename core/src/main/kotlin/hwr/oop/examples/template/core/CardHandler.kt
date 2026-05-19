package hwr.oop.examples.template.core

class CardHandler(cards: List<Card>) {
    private val cardsById: Map<CardNames, Card> =
        cards.associateBy { it.name }

    fun findCard(id: CardNames): Card? {
        return cardsById[id]
    }
}
