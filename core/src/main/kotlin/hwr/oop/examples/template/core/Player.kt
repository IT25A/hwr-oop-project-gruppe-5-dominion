package hwr.oop.examples.template.core

data class Player(internal val id: PlayerId, internal val cards: PlayerCards){

    fun id() = id

    fun insert(card: Card): Player {
        return Player(id, cards.insert(card))
    }

    fun draw(count: Int): Player {
        return Player(id, cards.draw(count))
    }

    fun endTurn(): Player {
        return Player(id, cards.endTurn())
    }

    fun use(card: Card): Player {
        return Player(id, cards.use(card))
    }

    fun currentHand(): List<Card> {
        return cards.hand
    }

    fun playArea(): List<String> {
        return emptyList()
    }

    fun currentDiscard(): List<Card> {
        return cards.discard
    }

    fun discard(selection: List<Card>): Player {
        if(cards.isValidSelection(selection)) {
            return Player(id, cards.removeSelection(selection))
        }

        throw InvalidSelectionException()
    }

    fun deckSize(): Int {
        return cards.stockSize() + cards.discardSize() + cards.handSize() + cards.usedSize()
    }

    fun holds(card: Card): Boolean {
        return cards.inHand(card)
    }



}
