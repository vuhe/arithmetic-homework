package top.vuhe.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.entity.Question

class JsonTest {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(JsonTest::class.java)
    }

    @Test
    @DisplayName("Json 转换测试")
    fun checkRepeatedFormula() {
        log.info("测试转换")
        val question = QuestionFactory.HalfHalf.produce()

        val json = JsonUnit.toJson(question)
        print(json)

        println()

        val testQuestion: Question = JsonUnit.fromJson(json)
        testQuestion.forEach { println(it) }
    }
}
