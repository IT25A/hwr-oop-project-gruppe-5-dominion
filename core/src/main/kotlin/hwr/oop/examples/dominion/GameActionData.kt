package hwr.oop.examples.dominion

sealed interface GameActionData {
    data class ActionData(val cardName: String): GameActionData
    data class TreasureData(val cardNames: List<String>): GameActionData
    data class PurchaseData(val int: Int): GameActionData
}
