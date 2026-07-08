package hwr.oop.examples.template.core

import kotlinx.serialization.Serializable

@Serializable
data class Pile(internal val card: Card, internal val quantity: Int){
    fun name() = card.stringName()
    fun count() = quantity

    fun draw(): Pile = copy(quantity = quantity - 1)
    fun isEmpty() = quantity == 0
    fun isType(other: Card) = other == card

    override fun equals(other: Any?): Boolean {
        return other is Pile && card == other.card
    }

    override fun hashCode(): Int {
        return card.hashCode()
    }

}
