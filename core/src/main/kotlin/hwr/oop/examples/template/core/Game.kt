package hwr.oop.examples.template.core

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlin.collections.orEmpty
import kotlin.collections.plus
@Serializable
class Game(
    private val status: GameStatus,
    private val state: BoardState,
    internal val activePlayer: ActivePlayer) {

    fun piles() = state.piles()
    fun players() = state.players + activePlayer.player

    fun isRunning() = status is GameStatus.Running

    fun next(): Game{
        requireGameRunning(status)
        return Game(GameStatus.Running, state.nextState(activePlayer), ActivePlayer.create(state.nextPlayer()))
    }

    fun play(card: Card): Game {
        requireGameRunning(status)
        return when(val result = activePlayer.play(card, state)){
            is PlayResult.Complete -> result.context.flush()
            is PlayResult.WaitingForChoice -> Game(GameStatus.WaitingForChoice(result.card, result.choices), state, activePlayer)
        }
    }

    fun purchase(card: Card): Game{
        requireGameRunning(status)
        return state.purchase(activePlayer, card)
    }

    fun answer(answer: AnsweredChoice): Game{
        require(status is GameStatus.WaitingForChoice)
        val name = answer.playerID
        val choices = status.choices[name] ?: throw NoPendingChoiceException(name)
        choices.first().verify(answer)

        val withAnswer = addAnswer(name, answer)
        val withoutChoice = removeChoice(name)
        if(withoutChoice.values.flatten().isEmpty()) {
            val result = activePlayer.resume(status.card, state, withAnswer)
            return result.context.flush()
        }

        return Game(GameStatus.WaitingForChoice(status.card, withoutChoice, withAnswer), state, activePlayer)
    }

    private fun addAnswer(name: String, answer: AnsweredChoice): Map<String,List<AnsweredChoice>> {
        require(status is GameStatus.WaitingForChoice)
        return status.answered + (
                    name to (status.answered[name].orEmpty() + answer)
                )
    }

    private fun removeChoice(name: String): Map<String, List<PendingChoice>> {
        require(status is GameStatus.WaitingForChoice)
        val choices = status.choices[name]?.drop(1)
        val others = status.choices.filterNot { it.key == name }
        if(choices.isNullOrEmpty()) {
            return others
        }

        return others + (name to choices)
    }

}