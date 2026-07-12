package hwr.oop.examples.dominion

interface DominionPersistence {
    fun load(gameId: String): GameInstance
    fun save(game: GameInstance)
}