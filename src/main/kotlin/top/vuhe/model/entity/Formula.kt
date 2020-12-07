package top.vuhe.model.entity

data class Formula(val a: Int, val op: Operator, val b: Int, val ans: Int) {
    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        private var a = 1
        private var op = Operator.plus
        private var b = 1

        fun a(a: Int): Builder {
            this.a = a
            return this
        }

        fun op(op: Operator): Builder {
            this.op = op
            return this
        }

        fun b(b: Int): Builder {
            this.b = b
            return this
        }

        fun ans(): Int {
            return op.calculate(a, b)
        }

        fun build(): Formula {
            return Formula(a, op, b, op.calculate(a, b))
        }
    }

    override fun toString(): String {
        return "${String.format("%2d", a)} $op ${String.format("%2d", b)} = "
    }
}
