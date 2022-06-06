package top.vuhe.tool

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.Context
import top.vuhe.model.Formula
import top.vuhe.model.Operator
import top.vuhe.model.Question

abstract class Factory<T> {
    abstract fun produce(): T
}

internal sealed class QuestionFactory(
    private val plusNum: Int,
    private val minusNum: Int
) : Factory<Question>() {
    private val log: Logger = LoggerFactory.getLogger(QuestionFactory::class.java)

    override fun produce(): Question {
        // 去重收集加法算式流
        val addStream = generateSequence(FormulaFactory.Add::produce)
            .distinct().take(plusNum)

        // 去重收集减法算式
        val subStream = generateSequence(FormulaFactory.Sub::produce)
            .distinct().take(minusNum)

        // 合并流并收集
        val formulas = addStream + subStream

        // 打乱
        formulas.shuffled()
        log.debug("创建一套习题")

        return Question.form(formulas.toList())
    }

    object AllPlus : QuestionFactory(50, 0)
    object AllMinus : QuestionFactory(0, 50)
    object HalfHalf : QuestionFactory(25, 25)
}

sealed class FormulaFactory(
    private val op: Operator
) : Factory<Formula>() {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(FormulaFactory::class.java)
    }

    /**
     * 获取一个算式
     *
     * 此算式已检查过数值符合要求
     *
     * @return 算式
     */
    override fun produce(): Formula {
        // 创建生产序列 检查并获取生产对象
        val formula = generateSequence {
            Formula(
                // 两个数数范围：1 ～ 99
                a = (1 until 100).random(),
                b = (1 until 100).random(),
                // 子类获取运算符
                op = op
            )
        }.filter { it.check() }.first()

        log.trace("生产一个算式")
        return formula
    }

    /**
     * 符合答案要求返回 true
     *
     * 符合答案标准：(0 <= ans <= 100)
     *
     * @receiver 算式构建者
     * @return 是否符合要求
     */
    private fun Formula.check(): Boolean {
        // 答案是否超出范围
        return ans in 0..Context.ANS_MAX
    }

    internal object Add : FormulaFactory(Operator.Plus)
    internal object Sub : FormulaFactory(Operator.Minus)
}
