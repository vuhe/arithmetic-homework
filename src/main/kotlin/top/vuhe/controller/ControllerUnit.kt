package top.vuhe.controller

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context

object ControllerUnit {
    private val log: Logger = LoggerFactory.getLogger(ControllerUnit::class.java)
    @ObsoleteCoroutinesApi
    private val thread = newSingleThreadContext("Controller-Work")

    /**
     * 创建新习题任务
     */
    @ObsoleteCoroutinesApi
    fun buildQuestion() = runBlocking(thread) {
        log.info("创建线程更新习题")
        val question = QuestionFactory.produce()
        Context.question = question
        log.info("创建完成")
    }
}