package hwr.oop.examples.template.core

class CardNotInHandException(card: Card): Exception("card $card not in hand")