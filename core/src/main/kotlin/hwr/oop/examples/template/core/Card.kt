package hwr.oop.examples.template.core

import hwr.oop.examples.template.core.cards.*
import org.jetbrains.annotations.TestOnly

enum class Card (private val card: CardDefinition) {

    COPPER(Copper()),
    SILVER(Silver()),
    GOLD(Gold()),
    ESTATE(Estate()),
    DUCHY(Duchy()),
    LABORATORY(Laboratory()),
    MARKET(Market()),
    SMITHY(Smithy()),
    VILLAGE(Village()),
    WOODCUTTER(Woodcutter()),
    FESTIVAL(Festival());

    companion object {
        private val apiValues = entries.associateBy { it.name }

        fun byName(name: String): Card {
            return apiValues[name]?: throw NoSuchCardException(name)
        }
    }

    fun stringName() = card.name

    fun isAction(): Boolean {
        return card.types.contains(CardType.ACTION)
    }

    fun isTreasure(): Boolean {
        return card.types.contains(CardType.TREASURE)
    }

    fun play(player: Player, currentStats: Stats, state: BoardState): PlayResult {
        val stats = currentStats.change(card.actions, card.buys, card.gold)
        val playerAfterDraw = player.draw(card.draw)
        val context = GameContext(ActivePlayer(playerAfterDraw.use(this), stats), state)
        return card.beginAction(context)
    }

    fun resume(context: GameContext, effect: ActiveEffect): PlayResult.Complete {
        return card.endAction(context, effect)
    }

    fun cost() = card.cost
    fun types() = card.types
}