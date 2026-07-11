package hwr.oop.examples.dominion

class NoTreasureException(card: Card) : Exception("expected card to be only treasure but got ${card.types()}")
