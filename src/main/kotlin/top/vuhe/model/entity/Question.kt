package top.vuhe.model.entity

class Question(formulas: List<Formula>) : Iterable<Question.Node> {
    private val questions = formulas.map { Node(formula = it) }

    enum class State {
        NotDo, Done, Wrong, Correct
    }

    data class Node(
        val formula: Formula,
        var state: State = State.NotDo,
        var userAns: Int? = null,
    )

    override fun iterator() = questions.iterator()
}
