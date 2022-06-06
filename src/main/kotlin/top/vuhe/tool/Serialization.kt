package top.vuhe.tool

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import top.vuhe.Context
import top.vuhe.model.Question
import java.io.File
import java.time.LocalDateTime

object Serialization {
    private val log = LoggerFactory.getLogger(Serialization::class.java)

    /** 生成习题文件 */
    fun buildQuestionToFile() = runBlocking(Dispatchers.IO) {
        val date = LocalDateTime.now()
        val prefix = "${Context.FILE_PATH}/${date.year}_${date.monthValue}_${date.dayOfMonth}_"

        // 混合生成
        QuestionFactory.HalfHalf.produce() writeTo File(prefix + "混合算式")

        // 全加法生成
        QuestionFactory.AllPlus.produce() writeTo File(prefix + "全加法")

        // 全减法生成
        QuestionFactory.AllMinus.produce() writeTo File(prefix + "全减法")
    }

    /** 从文件读取习题数据 */
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
        question writeTo File(filePath)
    }

    private infix fun Question.writeTo(file: File) {
        file.writeText(Json.encodeToString(this))
    }
}
