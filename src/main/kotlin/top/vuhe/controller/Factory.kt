package top.vuhe.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context
import top.vuhe.model.entity.Formula
import top.vuhe.model.entity.Formula.Builder
import top.vuhe.model.entity.Operator
import top.vuhe.model.entity.Question
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.NoSuchElementException
import kotlin.random.Random

abstract class Factory<T> {
    abstract fun produce(): T
}

internal object QuestionFactory : Factory<Question>() {
    private val log: Logger = LoggerFactory.getLogger(QuestionFactory::class.java)

    override fun produce(): Question {
        // 加法
        val addFormula: Factory<Formula> = AddFormulaFactory
        var addStream = Stream.generate(addFormula::produce)
        // 并行化加法算式流
        // 并行化加法算式流
        addStream = addStream.parallel() // 忽略顺序
            .unordered() // 去重
            .distinct() // 取一定的加法算式
            .limit(Context.PLUS_NUM)

        // 减法

        // 减法
        val subFormula: Factory<Formula> = SubFormulaFactory
        var subStream = Stream.generate(subFormula::produce)
        // 并行化减法算式
        // 并行化减法算式
        subStream = subStream.parallel() // 忽略顺序
            .unordered() // 去重
            .distinct() // 取一定的减法算式
            .limit(Context.MINUS_NUM)

        // 合并流并收集

        // 合并流并收集
        val formulas = Stream.concat(addStream, subStream)
            .unordered().parallel().collect(Collectors.toList())
        // 打乱
        // 打乱
        formulas.shuffle()
        log.debug("创建一套习题")

        return Question.from(formulas)
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
        // 创建并行生产流
        val builderStream = Stream.generate(this::build)
        val builderOp = builderStream.parallel()
            // 检查答案
            .filter(this::checkFormula)
            .limit(1)
            // 获取一个
            .findFirst()
        val builder: Builder = if (builderOp.isPresent) {
            builderOp.get()
        } else {
            log.error("生产错误")
            throw NoSuchElementException("生产错误")
        }

        log.trace("生产一个算式")
        return builder.build()
    }

    /**
     * 获取一个运算符
     *
     * 此方法行为由实现的子类控制
     *
     * @return 运算符
     */
    protected abstract fun getOp(): Operator

    private fun build(): Builder {
        return Formula.builder()
            // 两个数数范围：1 ～ 99
            .a(RANDOM_NUM.nextInt(99) + 1)
            .b(RANDOM_NUM.nextInt(99) + 1)
            // 子类获取运算符
            .op(getOp())
    }

    /**
     * 符合答案要求返回 true
     *
     * 符合答案标准：(0 <= ans <= 100)
     *
     * @param builder 算式构建者
     * @return 是否符合要求
     */
    private fun checkFormula(builder: Builder): Boolean {
        val ans = builder.ans()
        // 答案是否超出范围
        return 0 <= ans && ans <= Context.ANS_MAX
    }
}

internal object AddFormulaFactory : FormulaFactory() {
    override fun getOp(): Operator {
        return Operator.plus
    }
}

internal object SubFormulaFactory : FormulaFactory() {
    override fun getOp(): Operator {
        return Operator.minus
    }
}