package hwr.oop.examples.template

import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.ports.out.SaveGamePort
import hwr.oop.examples.dominion.ports.out.LoadGameByIdPort
import hwr.oop.examples.dominion.ports.out.GameRepository
import kotlinx.serialization.json.Json
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path

private val json = Json {
	prettyPrint = true
	ignoreUnknownKeys = true
}

class FileSystemPersistence(
	configuration: FileSystemPersistenceConfiguration,
	private val fileSystem: FileSystem = FileSystem.SYSTEM,
) : GameRepository, SaveGamePort{

	private val directory = configuration.directory

	override fun save(game: GameInstance) {
		val gameId= game.id()
		val path = path(gameId)
		fileSystem.write(path){
			writeUtf8(json.encodeToString<GameInstance>(game))
		}

	}

	override fun loadByid(gameId: GameID): GameInstance {
		val path = path(gameId)
		val readString = try {
			fileSystem.read(path) {
				readUtf8()
			}
		} catch (e: FileNotFoundException) {
			throw LoadGameByIdPort.CouldNotLoadException(gameId, e)
		}
		return json.decodeFromString<GameInstance>(readString)
	}

	private fun path(gameId: GameID): Path {
		return directory / "${gameId.value}.json"
	}

}

