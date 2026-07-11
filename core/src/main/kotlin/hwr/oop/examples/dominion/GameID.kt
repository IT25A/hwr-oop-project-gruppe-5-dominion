package hwr.oop.examples.dominion
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@JvmInline

value class GameID(val value: String) {
    companion object {
        fun random(): GameID = GameID(UUID.randomUUID().toString())
        fun from(uuid: UUID): GameID = GameID(uuid.toString())
    }
    fun uuid(): UUID = UUID.fromString(value)
}