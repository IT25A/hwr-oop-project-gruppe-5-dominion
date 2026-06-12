package hwr.oop.examples.template.core


class ActivePlayer(
    internal val player: Player,
    internal val stats: Stats)
{
    companion object {
        fun create(player: Player): ActivePlayer {
            return ActivePlayer(player, initialStats)
        }

        private val initialStats = Stats(1, 1, 0)
    }

    fun actions() = stats.actions
    fun buys() = stats.purchases
    fun coins() = stats.money

    fun id() = player.id

    fun play(card: Card, game: BoardState): PlayResult {
        if(player.holds(card)) {
            return card.play(player, stats, game)
        }

        throw CardNotInHandException(card)
    }

    fun resume(game: BoardState, effect: ActiveEffect): PlayResult.Complete {
        return effect.card.resume(GameContext(this, game), effect)
    }

    fun canAfford(cost: Int) = stats.money > cost

    fun purchase(card: Card): ActivePlayer {
        if(stats.purchases < 1){
            throw PurchaseException("not enough buys")
        }
        if(canAfford(card.cost())){
            return ActivePlayer(player.insert(card), stats.change(0, -1, -card.cost()))
        }

        throw PurchaseException("not enough money")
    }

    fun endTurn(): Player {
        return player.endTurn()
    }

}