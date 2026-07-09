package hwr.oop.examples.dominion

data class GamePendingChoice(
    val playerId: PlayerId,
    val choiceType: String,
    val description: String,
    val options: List<String>,
    val minSelections: Int,
    val maxSelections: Int
)