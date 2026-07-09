package hwr.oop.examples.dominion

class NoActionException(card: Card) : Exception("expected card to be action but only got ${card.types()}")