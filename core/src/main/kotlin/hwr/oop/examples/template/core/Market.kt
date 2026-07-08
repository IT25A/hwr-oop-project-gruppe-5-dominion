package hwr.oop.examples.template.core

import kotlinx.serialization.Serializable

@Serializable
class Market(internal val piles: Set<Pile>, private val emptyPiles: Int = 0) {
    fun emptyPiles(required: Int) = emptyPiles >= required

    fun piles(predicate: (Card) -> Boolean) = piles.filter{ predicate(it.card) }

    fun purchase(activePlayer: ActivePlayer, card: Card): PurchaseResult {
        val pile = piles.firstOrNull() { it.isType(card) } ?: throw PurchaseException("no such pile")
        return PurchaseResult(drawFrom(pile), activePlayer.purchase(pile.card))
    }

    fun drawFrom(pile: Pile): Market {
        val pileAfterDraw = pile.draw()

        if(pileAfterDraw.isEmpty()){
            return removePile(pile)
        }

        return replacePile(pile, pileAfterDraw)
    }

    private fun removePile(pile: Pile): Market{
        return Market(piles - pile, emptyPiles + 1)
    }

    private fun replacePile(pile: Pile, newPile: Pile): Market {
        return Market(piles - pile + newPile)
    }
}