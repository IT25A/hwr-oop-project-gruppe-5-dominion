package hwr.oop.examples.template.core

sealed interface PlayResult {
    data class Complete(val context: GameContext): PlayResult
    data class WaitingForChoice(val card: Card, val choices: Map<String, List<PendingChoice>>, val answered: Map<String, List<AnsweredChoice>>): PlayResult
}