package top.vuhe.model.entity

@kotlinx.serialization.Serializable
class Question(private val questions: List<Node>) : Iterable<Question.Node> by questions {

    enum class State {
        NotDo, Done, Wrong, Correct
    }

    @kotlinx.serialization.Serializable
    data class Node(
        val formula: Formula,
        var state: State = State.NotDo,
        var userAns: Int? = null,
    )

    companion object {
        fun form(formulas: List<Formula>) = Question(formulas.map { Node(formula = it) })
    }
}
