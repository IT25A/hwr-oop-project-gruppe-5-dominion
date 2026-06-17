package hwr.oop.examples.template.core
fun<T> List<T>.containsAllCopiesOf(other: List<T>): Boolean {
val counts = groupingBy { it }.eachCount().toMutableMap()

for (item in other) {
    val remaining = counts[item] ?: return false
    if (remaining == 0) return false
    counts[item] = remaining - 1
}

return true
}

fun<T> List<T>.subtractCopiesOf(other: List<T>): List<T> {
require(containsAllCopiesOf(other))

val counts = other.groupingBy { it }.eachCount().toMutableMap()

return filter { item ->
    val remaining = counts[item] ?: 0

    if (remaining > 0) {
        counts[item] = remaining - 1
        false
    } else {
        true
    }
}
}