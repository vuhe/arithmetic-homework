package top.vuhe.model.entity

enum class Operator {
    // 加法
    plus() {
        override fun calculate(a: Int, b: Int): Int {
            return a + b
        }

        override fun toString(): String {
            return "+"
        }
    },

    // 减法
    minus() {
        override fun calculate(a: Int, b: Int): Int {
            return a - b
        }

        override fun toString(): String {
            return "-"
        }
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