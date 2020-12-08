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

    /**
     * 概率和
     */
    private const val PROBABILITY_SUM = 100

    /**
     * 加法算式数量
     */
    var PLUS_NUM = 25
        @Synchronized get
        private set

    /**
     * 减法算式数量
     */
    var MINUS_NUM = 25
        @Synchronized get
        private set

    var question: Question = Question(ArrayList())
        @Synchronized get
        @Synchronized set

    @Synchronized
    fun setProportionNumber(plus: Int, minus: Int) {
        if (plus + minus != PROBABILITY_SUM) {
            log.error("概率和不为 100% !")
            throw IllegalArgumentException("The calculation formula accounts for not 100%.")
        }
        if (plus < 0) {
            log.error("加法算式比例小于 0 !")
            throw IllegalArgumentException("The ratio of addition formula cannot be less than zero.")
        }
        if (minus < 0) {
            log.error("减法算式比例小于 0 !")
            throw IllegalArgumentException("The ratio of subtraction formula cannot be less than zero.")
        }
        // 百分比转换为数量
        PLUS_NUM = (plus * 0.01 * FORMULA_NUM).toInt()
        MINUS_NUM = FORMULA_NUM - PLUS_NUM
    }
}