package hwr.oop.examples.template

import hwr.oop.examples.dominion.GameID
import hwr.oop.examples.dominion.GameInstance
import hwr.oop.examples.dominion.GamePhase
import hwr.oop.examples.dominion.GamePhases.DominionActionPhase
import hwr.oop.examples.dominion.GamePhases.DominionPendingEffectPhase
import hwr.oop.examples.dominion.GamePhases.DominionPurchasePhase
import hwr.oop.examples.dominion.ports.out.SaveGamePort
import hwr.oop.examples.dominion.ports.out.LoadGameByIdPort
import hwr.oop.examples.dominion.ports.out.GameRepository
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path

private val json = Json {
	prettyPrint = true
	ignoreUnknownKeys = true

	serializersModule = SerializersModule {
		polymorphic(GamePhase::class) {
			subclass(DominionActionPhase::class)
			subclass(DominionPurchasePhase::class)
			subclass(DominionPendingEffectPhase::class)
		}
	}
}

class FileSystemPersistence(
	configuration: FileSystemPersistenceConfiguration,
	private val fileSystem: FileSystem = FileSystem.SYSTEM,
) : GameRepository {

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

