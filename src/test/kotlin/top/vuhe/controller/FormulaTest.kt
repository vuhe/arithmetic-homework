package top.vuhe.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.entity.Formula
import java.util.stream.Stream

class FormulaTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(FormulaTest::class.java)
        /**
         * 默认检验算式数
         */
        private const val N: Long = 100000

        /**
         * 加法算式工厂
         */
        private var addFactory = AddFormulaFactory

        /**
         * 减法算式工厂
         */
        private var subFactory = SubFormulaFactory

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
        fun factoryStream(): Stream<Factory<Formula>> {
            return Stream.of(addFactory, subFactory)
        }
    }

    @ParameterizedTest
    @DisplayName("运算数测试")
    @MethodSource("factoryStream")
    fun checkCalculatedValue(factory: Factory<Formula?>) {
        log.info("开始运算数区间 (0, 100) 测试")
        val stream: Stream<Formula> = Stream.generate(factory::produce)
        stream.parallel()
            .limit(N)
            .unordered()
            .forEach { (a, _, b) ->
                Assertions.assertTrue(a in 1..99)
                Assertions.assertTrue(b in 1..99)
            }
        log.info("运算数检测完成，符合要求")
    }

    @ParameterizedTest
    @DisplayName("运算结果测试")
    @MethodSource("factoryStream")
    fun checkFormulaAns(factory: Factory<Formula?>) {
        log.info("开始运算结果区间 [0, 100] 测试")
        val stream: Stream<Formula> = Stream.generate(factory::produce)
        stream.parallel()
            .limit(N)
            .unordered()
            .forEach { (_, _, _, ans) ->
                Assertions.assertTrue(ans in 0..100)
            }
        log.info("运算结果检测完成，符合要求")
    }
}