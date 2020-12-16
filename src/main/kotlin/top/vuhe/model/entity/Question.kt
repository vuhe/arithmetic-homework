package top.vuhe.model.entity

class Question(formulas: List<Formula>) : Iterable<Question.Node> {
    private val questions: List<Node>

    init {
        val list = ArrayList<Node>(formulas.size + 1)
        for (f in formulas) {
            list.add(Node(
                formula = f,
                ans = f.ans
            ))
        }
        questions = list
    }

    enum class State {
        NotDo, Wrong, Correct
    }

    data class Node(
        val formula: Formula,
        val ans: Int,
        var state: State = State.NotDo,
        var userAns: Int? = null,
    )

    override fun iterator(): Iterator<Node> {
        return questions.iterator()
    }
}
