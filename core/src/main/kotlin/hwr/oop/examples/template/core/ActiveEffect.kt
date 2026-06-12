package hwr.oop.examples.template.core

import kotlin.collections.orEmpty
import kotlin.collections.plus

class ActiveEffect(
    val card: Card,
    val choices: Map<PlayerId, List<PendingChoice>>,
    val answered: Map<PlayerId, List<AnsweredChoice>> = emptyMap()
) {
    fun answer(answer: AnsweredChoice): ActiveEffect {
        val id = PlayerId(answer.playerID)
        val choicesOf = choices[id] ?: throw NoPendingChoiceException(id.value)
        choicesOf.first().verify(answer)

        val withAnswer = addAnswer(id, answer)
        val withoutChoice = removeChoice(id)

        return ActiveEffect(card, withoutChoice, withAnswer)
    }

    fun isResolved() = choices.values.flatten().isEmpty()

    private fun addAnswer(name: PlayerId, answer: AnsweredChoice): Map<PlayerId, List<AnsweredChoice>> {
        return answered + (
                name to (answered[name].orEmpty() + answer)
                )

    }

    private fun removeChoice(name: PlayerId): Map<PlayerId, List<PendingChoice>> {
        val new = choices[name]?.drop(1)
        val others = choices.filterNot { it.key == name }
        if (new.isNullOrEmpty()) {
            return others
        }

        return others + (name to new)
    }


}
