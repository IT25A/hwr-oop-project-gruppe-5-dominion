package hwr.oop.examples.template.core

class PendingChoiceBuilder {

    private var playerId: PlayerId = PlayerId("")
    private var choiceType: String = "" //replace with enum
    private var description: String = ""
    private val options: MutableList<String> = mutableListOf()
    private var minSelections: Int = 1
    private var maxSelections: Int = 1

    fun duplicate(): PendingChoiceBuilder {
        val builder = PendingChoiceBuilder()
        builder.playerId = playerId
        builder.choiceType = choiceType
        builder.description = description
        builder.minSelections = minSelections
        builder.maxSelections = maxSelections
        return builder
    }

    fun playerId(id: PlayerId) = apply {
        this.playerId = id
    }

    fun type(type: String) = apply {
        this.choiceType = type
    }

    fun description(desc: String) = apply {
        this.description = desc
    }

    fun options(opts: List<String>) = apply {
        this.options.clear()
        this.options.addAll(opts)
    }

    fun min(min: Int) = apply {
        this.minSelections = min
    }

    fun max(max: Int) = apply {
        this.maxSelections = max
    }

    fun build(): PendingChoice {
        require(playerId.value.isNotBlank()) { "playerId missing" }
        require(choiceType.isNotBlank()) { "choiceType missing" }
        require(options.isNotEmpty()) { "options missing" }
        require(minSelections <= maxSelections) { "invalid selection bounds" }

        return PendingChoice(
            playerId = playerId,
            choiceType = choiceType,
            description = description,
            options = options,
            minSelections = minSelections,
            maxSelections = maxSelections
        )
    }
}