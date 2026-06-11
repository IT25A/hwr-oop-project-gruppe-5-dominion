package hwr.oop.examples.template.core

sealed interface GameStatus {
    object Running : GameStatus
    data class WaitingForChoice(
        val card: Card,
        val choices: Map<String, List<PendingChoice>>,
        val answered: Map<String, List<AnsweredChoice>> = emptyMap()
    ) : GameStatus
}