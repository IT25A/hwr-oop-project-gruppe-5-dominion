package hwr.oop.examples.template.core

class PlayerCards(private val stock: List<Card> = defaultInitialStock,
                  internal val discard: List<Card> = emptyList(),
                  internal val hand: List<Card> = emptyList(),
                  internal val used: List<Card> = emptyList())
{

    companion object {
        val defaultInitialStock = List(4){ Card.COPPER } + List(3){ Card.ESTATE }
    }

    fun endTurn(): PlayerCards {
        return PlayerCards(stock, discardedCards())
    }

    private fun discardedCards() = discard + hand + used

    fun draw(count: Int): PlayerCards {
        if(count == 0)  {
            return this
        }

        val remaining: Int = stock.size - count
        val drawn = stock.takeLast(count)
        if (remaining >= 0) {
            return drawFromStock(drawn, remaining)
        }

        val missing = remaining * -1
        if (discard.size < missing) {
            return drawAsManyAsPossible(drawn)
        }

        return reshuffleAndDraw(drawn, missing)
    }

    private fun drawnCards(drawn: List<Card>) = hand + drawn

    private fun drawFromStock(drawn: List<Card>, remaining: Int): PlayerCards {
        val remainder = stock.take(remaining)
        return PlayerCards(remainder, discard, drawnCards(drawn), used)
    }

    private fun drawAsManyAsPossible(drawn: List<Card>): PlayerCards {
        return PlayerCards(emptyList(), emptyList(), drawnCards(drawn) + discard, used)
    } // this means the player has fewer cards then he is trying to draw which IS a valid case

    private fun reshuffleAndDraw(drawn: List<Card>, missing: Int): PlayerCards{
        val refill = PlayerCards(discard.shuffled(), emptyList(), drawnCards(drawn), used)
        return refill.draw(missing)
    }

    fun use(card: Card): PlayerCards {
        require(hand.contains(card))
        return PlayerCards(stock, discard, hand - card, used + card)
    }

   fun insert(card: Card): PlayerCards {
       return PlayerCards(stock, discard, hand, used + card)
   }

    fun inHand(card: Card): Boolean {
        return hand.contains(card)
    }

    fun isValidSelection(selection: List<Card>): Boolean{
        return hand.containsAllCopiesOf(selection)
    }

    fun removeSelection(selection: List<Card>): PlayerCards {
        return PlayerCards(stock, discard + selection, hand.subtractCopiesOf(selection), used)
    }


    fun handSize() = hand.size
    fun usedSize() = used.size
    fun stockSize() = stock.size
    fun discardSize() = discard.size

}
