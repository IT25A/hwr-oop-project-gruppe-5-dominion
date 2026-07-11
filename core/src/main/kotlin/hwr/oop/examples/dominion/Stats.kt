package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
data class Stats(
    val actions: Int,
    val purchases: Int,
    val money: Int
){
    fun change(actions: Int, purchases: Int, money: Int): Stats {
        return Stats(this.actions + actions, this.purchases + purchases, this.money + money)
    }
}