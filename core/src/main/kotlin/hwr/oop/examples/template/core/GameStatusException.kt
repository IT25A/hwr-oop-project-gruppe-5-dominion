package hwr.oop.examples.template.core

class GameStatusException(
    actual: String,
    required: String
) : Exception("required phase is: $required but is $actual")
