package hwr.oop.examples.dominion

data class AnsweredChoice(
    val playerId: PlayerId,
    val selectedOptions: List<String>)