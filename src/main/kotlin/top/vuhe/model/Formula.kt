package top.vuhe.model

@kotlinx.serialization.Serializable
data class Formula(val a: Int, val op: Operator, val b: Int) {
    val ans: Int = op.calculate(a, b)

    override fun toString(): String {
        return "${String.format("%2d", a)} $op ${String.format("%2d", b)} = "
    }
}
