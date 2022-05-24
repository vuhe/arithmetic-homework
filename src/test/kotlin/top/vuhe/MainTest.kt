package top.vuhe

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.controller.QuestionFactory
import top.vuhe.model.entity.Question

class MainTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(MainTest::class.java)
    }

    /**
     * 版本 v0.x 测试
     *
     * 测试算式的产生和运算
     */
    @Test
    @DisplayName("面向过程测试")
    fun test() {
        log.info("v0.x 面向过程测试")
        val question: Question = QuestionFactory.HalfHalf.produce()
        for ((i, formula) in question.withIndex()) {
            if (i != 0 && i % 5 == 0) {
                println()
            }
            print("$formula${String.format("%3d   ", formula.userAns)}")
        }
        println("\n")
    }
}
