package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
data class GameMarket(internal val piles: Set<Pile>, private val emptyPiles: Int = 0) {
    fun emptyPiles(required: Int) = emptyPiles >= required

    fun piles(predicate: (Card) -> Boolean) = piles.filter{ predicate(it.card) }

    fun purchase(activePlayer: ActivePlayer, card: Card): PurchaseResult {
        val pile = piles.firstOrNull() { it.isType(card) } ?: throw PurchaseException("no such pile")
        return PurchaseResult(drawFrom(pile), activePlayer.purchase(pile.card))
    }

    fun gameEndingRequirementMet() = emptyPiles >= 3

    fun drawFrom(pile: Pile): GameMarket {
        val pileAfterDraw = pile.draw()

        if(pileAfterDraw.isEmpty()){
            return removePile(pile)
        }

        return replacePile(pile, pileAfterDraw)
    }

    private fun removePile(pile: Pile): GameMarket{
        return GameMarket(piles - pile, emptyPiles + 1)
    }

    private fun replacePile(pile: Pile, newPile: Pile): GameMarket {
        return GameMarket(piles - pile + newPile, emptyPiles)
    }

}