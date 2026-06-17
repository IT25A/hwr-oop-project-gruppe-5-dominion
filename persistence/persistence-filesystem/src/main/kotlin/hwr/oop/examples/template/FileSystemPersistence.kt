package hwr.oop.examples.template

import hwr.oop.examples.template.core.DominionPersistence
import hwr.oop.examples.template.core.Game
import hwr.oop.examples.template.core.GameInstance
import okio.FileSystem

class FileSystemPersistence(
	configuration: FileSystemPersistenceConfiguration,
	private val fileSystem: FileSystem = FileSystem.SYSTEM,
	private val games: Map<String, GameInstance> = emptyMap(),
) : DominionPersistence{

	private val directory = configuration.directory



	override fun load(gameId: String): GameInstance {
		 return games[gameId] ?: loadFromFile(gameId)
	}

	private fun loadFromFile(gameId: String): GameInstance {
		TODO("missing")
	}

	override fun save(game: GameInstance) {
		games.filterNot { it.value == game }
		TODO("Not yet implemented")
	}
}

