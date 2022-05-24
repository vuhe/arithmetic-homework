package top.vuhe.model

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.entity.Question

/**
 * @author vuhe
 */
object Context {
    private val log: Logger = LoggerFactory.getLogger(Context::class.java)

    /**
     * 算式数量
     */
    const val FORMULA_NUM = 50

    /**
     * 算式结果最大值
     */
    const val ANS_MAX = 100

    const val FILE_PATH: String = "./exercise"

    var file: String? = null
        @Synchronized get
        @Synchronized set

    var question: Question = Question(emptyList())
        @Synchronized get
        @Synchronized set
}
