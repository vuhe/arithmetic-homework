package top.vuhe

import top.vuhe.model.Question

/** 全局上下文 */
object Context {

    /** 算式数量 */
    const val FORMULA_NUM = 50

    /** 算式结果最大值 */
    const val ANS_MAX = 100

    const val FILE_PATH: String = "./exercise"

    var file: String? = null
        @Synchronized get
        @Synchronized set

    var question: Question = Question(emptyList())
        @Synchronized get
        @Synchronized set
}
