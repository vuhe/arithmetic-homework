package top.vuhe.model.entity

class Question(private val formulas: List<Formula>) : Iterable<Formula> {
    override fun iterator(): Iterator<Formula> {
        return formulas.iterator()
    }
}
