package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
data class AnsweredChoice(
    val playerId: PlayerId,
    val selectedOptions: List<String>){
}
