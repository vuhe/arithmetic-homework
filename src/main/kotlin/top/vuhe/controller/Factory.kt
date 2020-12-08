package top.vuhe.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context
import top.vuhe.model.entity.Formula
import top.vuhe.model.entity.Operator
import top.vuhe.model.entity.Question
import kotlin.random.Random

abstract class Factory<T> {
    abstract fun produce(): T
}

internal object QuestionFactory : Factory<Question>() {
    private val log: Logger = LoggerFactory.getLogger(QuestionFactory::class.java)

    override fun produce(): Question {
        // 去重收集加法算式流
        var addStream = generateSequence(AddFormulaFactory::produce)
        addStream = addStream.distinct().take(Context.PLUS_NUM)

        // 去重收集减法算式
        var subStream = generateSequence(SubFormulaFactory::produce)
        subStream = subStream.distinct().take(Context.MINUS_NUM)

        // 合并流并收集
        val formulas = ArrayList<Formula>(Context.FORMULA_NUM + 1)
        formulas.addAll(addStream.toList())
        formulas.addAll(subStream.toList())

        // 打乱
        formulas.shuffle()
        log.debug("创建一套习题")

        return Question(formulas)
    }
}

internal abstract class FormulaFactory : Factory<Formula>() {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(FormulaFactory::class.java)

        /**
         * 随机数生产器
         */
        private val RANDOM_NUM = Random(47)
    }

    /**
     * 获取一个算式
     *
     * 此算式已检查过数值符合要求
     *
     * @return 算式
     */
    override fun produce(): Formula {
        // 创建生产序列
        val builderStream = generateSequence(this::build)
        // 检查并获取生产对象
        val formula = builderStream.filter(this::checkFormula).first()

        log.trace("生产一个算式")
        return formula
    }

    /**
     * 获取一个运算符
     *
     * 此方法行为由实现的子类控制
     *
     * @return 运算符
     */
    protected abstract fun getOp(): Operator

    private fun build(): Formula {
        return Formula(
            // 两个数数范围：1 ～ 99
            a = RANDOM_NUM.nextInt(99) + 1,
            b = RANDOM_NUM.nextInt(99) + 1,
            // 子类获取运算符
            op = getOp()
        )
    }

    /**
     * 符合答案要求返回 true
     *
     * 符合答案标准：(0 <= ans <= 100)
     *
     * @param builder 算式构建者
     * @return 是否符合要求
     */
    private fun checkFormula(builder: Formula): Boolean {
        // 答案是否超出范围
        return builder.ans in 0..Context.ANS_MAX
    }
}

internal object AddFormulaFactory : FormulaFactory() {
    override fun getOp() = Operator.Plus
}

internal object SubFormulaFactory : FormulaFactory() {
    override fun getOp() = Operator.Minus
}