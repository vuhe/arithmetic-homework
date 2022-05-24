package top.vuhe.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import top.vuhe.model.Context
import top.vuhe.model.entity.Question
import java.io.File
import java.time.LocalDateTime

object ControllerUnit {
    private val log = LoggerFactory.getLogger(ControllerUnit::class.java)

    /** 生成习题文件 */
    fun buildQuestionToFile() = runBlocking(Dispatchers.IO) {
        val date = LocalDateTime.now()
        val prefix = "${Context.FILE_PATH}/${date.year}_${date.monthValue}_${date.dayOfMonth}_"

        // 混合生成
        writeQuestion(
            QuestionFactory.HalfHalf.produce(),
            File(prefix + "混合算式")
        )

        // 全加法生成
        writeQuestion(
            QuestionFactory.AllPlus.produce(),
            File(prefix + "全加法")
        )

        // 全减法生成
        writeQuestion(
            QuestionFactory.AllMinus.produce(),
            File(prefix + "全减法")
        )
    }

    /**
     * 从文件读取习题数据
     *
     * @param file 文件
     */
    fun readQuestionFromFile(file: File) = runBlocking(Dispatchers.IO) {
        log.info("读取文件")
        // 检查是否是文件
        require(file.isFile) { "传入文件错误" }

        val json = file.readText()
        Context.question = Json.decodeFromString(json)
        log.info("读取完成")
    }

    fun writeQuestionToFile(question: Question) = runBlocking(Dispatchers.IO) {
        val filePath = "${Context.FILE_PATH}/${Context.file}"
        writeQuestion(question, File(filePath))
    }

    private fun writeQuestion(question: Question, file: File) {
        val json = Json.encodeToString(question)
        file.writeText(json)
    }
}
