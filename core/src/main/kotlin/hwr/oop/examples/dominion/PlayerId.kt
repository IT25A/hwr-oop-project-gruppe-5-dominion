package hwr.oop.examples.dominion

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class PlayerId(val value: String)
