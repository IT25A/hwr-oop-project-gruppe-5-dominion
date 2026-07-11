package hwr.oop.examples.dominion

class NoPendingChoiceException(playerID: String): Exception("not waiting for choice of player: $playerID")
