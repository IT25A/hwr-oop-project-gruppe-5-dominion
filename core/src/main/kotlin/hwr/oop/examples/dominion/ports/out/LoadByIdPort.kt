package hwr.oop.examples.dominion.ports.out

import hwr.oop.examples.dominion.GameInstance

import hwr.oop.examples.doppelkopf_2026.core.GameId

interface LoadGameByIdPort {

    fun loadByid(gameId: GameId): Game

    class CouldNotLoadException(
        gameId: GameId,
        cause: Exception? = null
    ) : RuntimeException(
        "Could not load game with id: $gameId",
        cause
    )

}