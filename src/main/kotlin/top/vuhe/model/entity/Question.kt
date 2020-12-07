package top.vuhe.model.entity

class Question private constructor(private val formulas: List<Formula>) : Iterable<Formula> {

    companion object {
        fun from(): Question {
            return Question(ArrayList())
        }

        fun from(formula: Formula): Question {
            return run {
                val list: MutableList<Formula> = ArrayList()
                list.add(formula)
                Question(list)
            }
        }

        fun from(formulas: List<Formula>): Question {
            return Question(formulas)
        }
    }

    override fun iterator(): Iterator<Formula> {
        return formulas.iterator()
    }
}
