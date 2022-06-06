package top.vuhe.tool

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.Context
import top.vuhe.model.Formula

class FormulaTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(FormulaTest::class.java)

        /**
         * 默认检验算式数
         */
        private const val N = 100_000

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            log.info("「算式」单元测试")
        }

        /**
         * 提供测试用方法工厂
         *
         * @return 算式工厂流
         */
        @JvmStatic
        fun factoryStream(): Array<Factory<Formula>> {
            return arrayOf(FormulaFactory.Add, FormulaFactory.Sub)
        }
    }

    @ParameterizedTest
    @DisplayName("运算数测试")
    @MethodSource("factoryStream")
    fun checkCalculatedValue(factory: Factory<Formula>) {
        log.info("开始运算数区间 (0, 100) 测试")

        generateSequence(factory::produce).take(N)
            .forEach { (a, _, b) ->
                Assertions.assertTrue(a in 1..99)
                Assertions.assertTrue(b in 1..99)
            }

        log.info("运算数检测完成，符合要求")
    }

    @ParameterizedTest
    @DisplayName("运算结果测试")
    @MethodSource("factoryStream")
    fun checkFormulaAns(factory: Factory<Formula>) {
        log.info("开始运算结果区间 [0, 100] 测试")

        generateSequence(factory::produce).take(N)
            .forEach {
                Assertions.assertTrue(it.ans in 0..Context.ANS_MAX)
            }

        log.info("运算结果检测完成，符合要求")
    }
}
