package top.vuhe.model.entity

class Question(formulas: List<Formula>) : Iterable<Question.Node> {
    private val questions: List<Node>

    init {
        val list = ArrayList<Node>(formulas.size + 1)
        for (f in formulas) {
            list.add(Node(
                formula = f,
            ))
        }
        questions = list
    }

    enum class State {
        NotDo, Done, Wrong, Correct
    }

    data class Node(
        val formula: Formula,
        var state: State = State.NotDo,
        var userAns: Int? = null,
    )

    override fun iterator(): Iterator<Node> {
        return questions.iterator()
    }
}
