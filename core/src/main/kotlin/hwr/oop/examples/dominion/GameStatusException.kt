package hwr.oop.examples.dominion

class GameStatusException(
    actual: String,
    required: String
) : Exception("required phase is: $required but is $actual")
