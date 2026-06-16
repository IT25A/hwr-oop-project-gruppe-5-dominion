package hwr.oop.examples.template.core

class GameStatusException(
    actual: GamePhase,
    required: GamePhase
) : Exception("required phase is: $required but is $actual")
