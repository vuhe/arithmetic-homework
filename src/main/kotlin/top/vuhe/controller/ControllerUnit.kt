package top.vuhe.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context

object ControllerUnit {
    private val log: Logger = LoggerFactory.getLogger(ControllerUnit::class.java)

    /**
     * 创建新习题任务
     */
    fun buildQuestion() = runBlocking(Dispatchers.IO) {
        log.info("创建线程更新习题")
        val question = QuestionFactory.produce()
        Context.question = question
        log.info("创建完成")
    }
}