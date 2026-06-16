package hwr.oop.examples.template.core

data class PendingChoice(
    val playerId: PlayerId,
    val choiceType: String,
    val description: String,
    val options: List<String>,
    val minSelections: Int,
    val maxSelections: Int
)