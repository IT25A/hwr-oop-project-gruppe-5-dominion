package hwr.oop.examples.template.core

import hwr.oop.examples.template.core.cards.*

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
    CELLAR(Cellar()),
    FESTIVAL(Festival());

    companion object {
        private val apiValues = entries.associateBy { it.name }

        fun byName(name: String): Card {
            return apiValues[name]?: throw NoSuchCardException(name)
        }
    }

    override fun toString() = card.name

    fun isAction(): Boolean {
        return card.types.contains(CardType.ACTION)
    }

    fun isTreasure(): Boolean {
        return card.types.contains(CardType.TREASURE)
    }

    fun play(player: Player, currentStats: Stats, state: BoardState): Game {
        val stats = currentStats.change(card.actions, card.buys, card.gold)
        val playerAfterDraw = player.draw(card.draw)
        val context = GameContext(ActivePlayer(playerAfterDraw.use(this), stats), state)
        val effect = card.getEffect(context)
        if(effect != null) {
            return effect.execute()
        }

        return context.flush()
    }

    fun cost() = card.cost
    fun types() = card.types
}