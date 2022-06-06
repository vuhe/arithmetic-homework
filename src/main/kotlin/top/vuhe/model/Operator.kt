package top.vuhe.model

@kotlinx.serialization.Serializable
enum class Operator {
    // 加法
    Plus {
        override fun calculate(a: Int, b: Int) = a + b
        override fun toString() = "+"
    },

    // 减法
    Minus {
        override fun calculate(a: Int, b: Int) = a - b
        override fun toString() = "-"
    };

    /**
     * 运算方法
     *
     * @param a 第一个运算数
     * @param b 第二个运算数
     * @return 运算结果
     */
    abstract fun calculate(a: Int, b: Int): Int
}
