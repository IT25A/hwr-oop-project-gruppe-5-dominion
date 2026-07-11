package hwr.oop.examples.dominion.ports.out
import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance


interface LoadGameByIdPort {

    fun loadByid(gameId: GameID): GameInstance
    fun loadByid(gameId: String): GameInstance = loadByid(GameID(gameId))

    class CouldNotLoadException(
        gameId: GameID,
        cause: Exception? = null
    ) : RuntimeException(
        "Could not load game with id: $gameId",
        cause
    )

}