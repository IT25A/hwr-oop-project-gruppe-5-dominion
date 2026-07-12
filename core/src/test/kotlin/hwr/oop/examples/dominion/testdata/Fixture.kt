package hwr.oop.examples.dominion.testdata

import hwr.oop.examples.dominion.ActivePlayer
import hwr.oop.examples.dominion.BoardState
import hwr.oop.examples.dominion.Card
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.GameMarket
import hwr.oop.examples.dominion.GamePhases.DominionActionPhase
import hwr.oop.examples.dominion.Pile
import hwr.oop.examples.dominion.Player
import hwr.oop.examples.dominion.PlayerCards
import hwr.oop.examples.dominion.PlayerId
import hwr.oop.examples.dominion.Stats

object Fixture {
    //Players
    private val p1_cards = PlayerCards(hand = listOf(Card.COPPER, Card.COPPER))
    private val p1 = Player(PlayerId("alpha"), p1_cards)

    private val p2_cards = PlayerCards(hand = listOf(Card.CELLAR, Card.WOODCUTTER))
    private val p2 = Player(PlayerId("beta"), p2_cards)

    private val p3_cards = PlayerCards(hand = listOf(Card.ESTATE, Card.ESTATE))
    private val p3 = Player(PlayerId("beta"), p3_cards)
    //Market
    private val pile_copper = Pile(Card.COPPER, 10)
    private val pile_estate = Pile(Card.ESTATE, 10)

    private val market = GameMarket(setOf(pile_copper, pile_estate), 1)
    //Game
    private val state = BoardState(market, listOf(p1, p2))

    private val stats = Stats(2, 3, 4)
    private val currentPlayer = ActivePlayer(p3, stats)
    private val phase = DominionActionPhase(state, currentPlayer)

    fun game() = GameInstance(
        phase,
        GameID.random()
    )

}