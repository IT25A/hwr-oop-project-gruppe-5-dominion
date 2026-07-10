package hwr.oop.examples.template

import hwr.oop.examples.dominion.DominionPersistence
import hwr.oop.examples.dominion.GameInstance
import okio.FileSystem
import kotlinx.serialization.json.Json
import okio.Path
import okio.FileNotFoundException

private val json = Json {
	prettyPrint = true
	ignoreUnknownKeys = true
}

class FileSystemPersistence(
	configuration: FileSystemPersistenceConfiguration,
	private val fileSystem: FileSystem = FileSystem.SYSTEM,
	private val games: Map<String, GameInstance> = emptyMap()
) : DominionPersistence{

	private val directory = configuration.directory

	override fun save(game: GameInstance) {
		val gameId= game.id()
		val path = path(gameId)
		fileSystem.write(path){
			writeUtf8(json.encodeToString<GameInstance>(game))
		}

	}

	override fun loadByid(gameId: GameInstance): GameInstance {
		 return games[gameId] ?: loadFromFile(gameId)
	}

	private fun loadFromFile(gameId: String): GameInstance {
		TODO("missing")
	}


}

