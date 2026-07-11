package hwr.oop.examples.dominion

class CardNotInHandException(card: Card): Exception("card $card not in hand")