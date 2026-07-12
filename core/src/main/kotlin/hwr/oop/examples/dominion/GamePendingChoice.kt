package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
data class GamePendingChoice(
    val playerId: PlayerId,
    val choiceType: String,
    val description: String,
    val options: List<String>,
    val minSelections: Int,
    val maxSelections: Int
) {
    fun assertValid(answer: AnsweredChoice) {
        require(answer.selectedOptions.size >= minSelections) { "too little choices selected" }
        require(answer.selectedOptions.size <= maxSelections) { "too many choices selected" }
    }
}