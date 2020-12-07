package top.vuhe.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context
import top.vuhe.model.entity.Formula
import top.vuhe.model.entity.Operator
import top.vuhe.model.entity.Question
import java.util.*
import java.util.stream.Stream
import kotlin.collections.HashSet

class QuestionTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(QuestionTest::class.java)

        /**
         * 默认检验问题数
         */
        private const val N: Long = 10000

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            log.info("「问题」单元测试")
        }
    }

    @Test
    @DisplayName("检查重复算式")
    fun checkRepeatedFormula() {
        log.info("开始运算式生成不重复测试")

        // 创建并行问题流
        val questionStream: Stream<Question> = Stream.generate(QuestionFactory::produce)
        questionStream.parallel()
            .unordered()
            // 长度限制
            .limit(N)
            // 对于每一个问题执行
            .forEach { question: Question ->
                checkEveryQuestionRepeatedFormula(
                    question
                )
            }
        log.info("算式检测完成，无重复")
    }

    @Test
    @DisplayName("检查运算符数量")
    fun checkNumberOfOperators() {
        log.info("开始运算符在指定范围测试")

        // 创建并行问题流
        val questionStream: Stream<Question> = Stream.generate(QuestionFactory::produce)
        questionStream.parallel()
            .unordered()
            // 长度限制
            .limit(N)
            // 对于每一个问题执行
            .forEach { question: Question ->
                checkEveryQuestionNumberOfOperators(
                    question
                )
            }
        log.info("运算符检查完成，符合要求")
    }

    private fun checkEveryQuestionRepeatedFormula(question: Question) {
        // 检查一个问题中是否有重复
        val set: MutableSet<Formula> = HashSet()
        // 获取迭代器
        question.iterator()
            // 检查每一个算式
            .forEachRemaining { formula: Formula ->
                // 断言是否存在
                Assertions.assertTrue(set.add(formula))
            }
    }

    private fun checkEveryQuestionNumberOfOperators(question: Question) {
        // 初始化映射
        val map: MutableMap<Operator, Long> = EnumMap(Operator::class.java)
        map[Operator.minus] = 0
        map[Operator.plus] = 0

        // 获取迭代器
        question.iterator()
            // 添加每一个运算符
            .forEachRemaining { (_, op) ->
                map[op] = map[op]!! + 1
            }

        // 断言加法数量一致
        Assertions.assertSame(
            Context.PLUS_NUM,
            map[Operator.plus]
        )
        // 断言减法数量一致
        Assertions.assertSame(
            Context.MINUS_NUM,
            map[Operator.minus]
        )
    }
}